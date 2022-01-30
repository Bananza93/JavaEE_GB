function onClickAddToCart(button, qnt) {
    button.disabled = true;
    fetch(window.location.origin + "/lesson7/cart/product?id=" + button.dataset.productId + "&qnt=" + qnt, {method: "POST"})
        .then(response => response.json())
        .then(body => renderCart(body))
        .finally(() => button.disabled = false);
}

function onClickRemoveFromCart(button, qnt) {
    button.disabled = true;
    fetch(window.location.origin + "/lesson7/cart/product?id=" + button.dataset.productId + "&qnt=" + qnt, {method: "DELETE"})
        .then(response => response.json())
        .then(body => renderCart(body))
        .finally(() => button.disabled = false);
}

function getAndRenderCart() {
    console.log(window.location.origin);
    fetch(window.location.origin + "/lesson7/cart")
        .then(response => response.json())
        .then(body => renderCart(body));
}

function displayCart() {
    document.getElementById("shoppingCart").classList.toggle('d-none');
}

function renderCart(cart) {
    var cartInnerHtml = `
    <div class="shopping-cart-header">
        <span class="lighter-text">Товары (</span>
        <span class="lighter-text">${cart.currentCart.length}</span>
        <span class="lighter-text">)</span>

    </div>
    <ul class="shopping-cart-items">`;
    cart.currentCart.forEach(item => {
        cartInnerHtml += `
        <li>
            <span class="item-name">
                ${item.title}
                <i class="fas fa-minus cart-control" data-product-id=\"${item.productId}\" onclick="onClickRemoveFromCart(this, 1)"></i>
                <i class="fas fa-plus cart-control" data-product-id=\"${item.productId}\" onclick="onClickAddToCart(this, 1)"></i>
            </span>
            <span class="item-price">${item.price} ₽</span>
            <span class="item-quantity">${item.qnt}</span>
        </li>`;
    });
    cartInnerHtml += `
    </ul>
    <div class="shopping-cart-total">
        <span class="lighter-text">Итог:</span>
        <span class="main-color-text">${cart.sumPrice} ₽</span>
    </div>
    <a href="#" class="btn btn-success w-100">Оформить заказ</a>`;
    document.getElementById("shoppingCart").innerHTML = cartInnerHtml;
}
