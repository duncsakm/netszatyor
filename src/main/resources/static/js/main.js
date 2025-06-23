document.addEventListener("DOMContentLoaded", function () {

    // Kosár ürítése megerősítés
    const clearCartBtn = document.getElementById("clear-cart-btn");
    if (clearCartBtn) {
        clearCartBtn.addEventListener("click", function (e) {
            if (!confirm("Biztosan törölni szeretnéd a teljes kosarat?")) {
                e.preventDefault();
            }
        });
    }

    // Mennyiség módosítása automatikusan beküldi az űrlapot
    document.querySelectorAll(".quantity-input").forEach(function (input) {
        input.addEventListener("change", function () {
            const form = input.closest("form");
            if (form) {
                form.submit();
            }
        });
    });

    // Fizetési mód kiválasztása (csak UI logika, backenden is ellenőrizni kell!)
    const fizetesiRadioButtons = document.querySelectorAll('input[name="fizetesiMod"]');
    fizetesiRadioButtons.forEach(function (radio) {
        radio.addEventListener("change", function () {
            fizetesiRadioButtons.forEach(btn => btn.parentElement.classList.remove("active"));
            radio.parentElement.classList.add("active");
        });
    });

});
