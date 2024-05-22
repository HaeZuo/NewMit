<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/styles/slick.css">
    <link rel="stylesheet" href="/styles/styles.css">

    <script src="/scripts/jquery-2.2.4.min.js"></script>
    <script src="/scripts/slick.min.js"></script>
    <script src="/scripts/scripts.js"></script>

</head>

        <div class="list-useByDate" id="useByDateDiv">
            <h2><span>소비기한 임박 식자재</span></h2>
            <ul id="useByDateUl">

            </ul>
        </div>
        <script>

            document.getElementById("useByDateUl").innerHTML = ``;
            httpRequest('POST', '/inqredients/selectExpirationDateImminentList', null, async function(success) {

                const data = await success.json();

                if(data['expirationDateImminentList'].length != 0) {
                    for(let curExpirationDateImminent of data['expirationDateImminentList']) {

                        document.getElementById("useByDateUl").insertAdjacentHTML('beforeend', `
                            <li>
                                <a href="#">
                                    <span><span>` + curExpirationDateImminent['REMAINING_EXPIRATION_DATE'] + `</span>일</span>
                                    <img src="data:image/jpeg;base64,` + curExpirationDateImminent['bannerImage'] + `" alt="">
                                    <p>` + curExpirationDateImminent['INGREDIENT_OWNED_NM'] + `</p>
                                </a>
                            </li>
                        `);
                    }
                } else {
                    document.getElementById("useByDateDiv").style.display = "none";
                }

            }, function(fail) {
               alert("에러!");
            });
        </script>