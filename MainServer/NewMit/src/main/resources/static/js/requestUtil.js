// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// 쿠키를 삭제하는 함수
function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

httpRequest = function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success(response);
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            }).then(res => {
                if (res.ok) {
                    return res.json();
                }
            }).then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                localStorage.setItem('access_token', result.accessToken);
                httpRequest(method, url, body, success, fail);
            }).catch(error => fail());
        } else {
            return fail();
        }
    });
}

const async = {};
async.httpRequest = async function(method, url, body, success, fail) {
    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                'Content-Type': 'application/json',
            },
            body: body,
        });

        if (response.status === 200 || response.status === 201) {
            success(response);
        } else if (response.status === 401 && getCookie('refresh_token')) {
            const refreshResponse = await fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            });

            if (refreshResponse.ok) {
                const result = await refreshResponse.json();
                localStorage.setItem('access_token', result.accessToken);
                await httpRequest(method, url, body, success, fail);
            } else {
                fail();
            }
        } else {
            fail();
        }
    } catch (error) {
        fail();
    }
}