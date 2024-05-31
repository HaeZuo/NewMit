recipeListComponents = {};

recipeListComponents.recipeAddCnt = 0;

recipeListComponents.insertStep = function (recipeInfo) {
    const id = ++recipeListComponents.recipeAddCnt;

    const listRecipeUl = document.getElementById("listRecipeUl");
    listRecipeUl.insertAdjacentHTML('beforeend', recipeListComponents.getRecipeInfoElement(id, recipeInfo));
}

recipeListComponents.getRecipeInfoElement = function(id, recipeInfo) {
    let recipeInfoElement = `
                    <li>
                        <a href="/recipe/viewDetailRecipe?recipeNo=` + recipeInfo['RECIPE_NO'] + `&mbNo=` + recipeInfo['MB_NO'] + `">
                            <img src="data:image/jpeg;base64,` + recipeInfo['mainImage'] + `" alt="">
                            <div>
                                <p class="r-chef"><span>` + recipeInfo['MB_NM'] + `</span> 요리사님</p>
                                <p class="r-title">` + recipeInfo['RECIPE_NM'] + `</p>
                                <div class="review-star">
                                    <ul>
                            `;

    const rating = recipeInfo['RATING'];

    const ratingFloor = Math.floor(Number(rating));

    for(let idx=0; idx<ratingFloor; idx++) {
        recipeInfoElement += '<li class="active"></li>';
    }

    for(let idx=0; idx<5-ratingFloor; idx++) {
        recipeInfoElement += '<li></li>';
    }

    recipeInfoElement += `
                                    </ul>
                                    <span>` + rating + `</span>
                                </div>
                                <div class="list-tag">
                                    <ul>
                                        <li>#칼칼한</li>
                                        <li>#깊은</li>
                                    </ul>
                                </div>
                            </div>
                        </a>
                    </li>
    `;

    return recipeInfoElement;
}