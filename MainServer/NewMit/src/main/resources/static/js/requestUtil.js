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

httpRequest = function (method, url, body, success, fail) {
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

httpRequest_FileInclusion = function(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
        contentType: false,
        processData: false
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

const Async = {}
Async.httpRequest = function (method, url, body, success, fail) {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url, false); // false는 동기 요청

    // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('access_token'));
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) { // 요청이 완료되었을 때
            if (xhr.status === 200 || xhr.status === 201) {
                success(xhr);
            } else if (xhr.status === 401 && getCookie('refresh_token')) {
                const refreshRequest = new XMLHttpRequest();
                refreshRequest.open('POST', '/api/token', false);
                refreshRequest.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('access_token'));
                refreshRequest.setRequestHeader('Content-Type', 'application/json');

                refreshRequest.onreadystatechange = function () {
                    if (refreshRequest.readyState === 4 && refreshRequest.status === 200) {
                        const result = JSON.parse(refreshRequest.responseText);
                        localStorage.setItem('access_token', result.accessToken);
                        httpRequest(method, url, body, success, fail);
                    } else {
                        fail();
                    }
                };

                refreshRequest.send(JSON.stringify({
                    refreshToken: getCookie('refresh_token')
                }));
            } else {
                fail();
            }
        }
    };

    xhr.send(body);
}