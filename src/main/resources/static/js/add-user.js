const emailInput = document.getElementById("email")
const passwordInput = document.getElementById("password")
const emailError = document.getElementById("emailErr")
const passwordError = document.getElementById("passwordErr")

emailInput.addEventListener("keyup", function () {
    emailError.style.display = "none"
})
passwordInput.addEventListener("keyup", function () {
    passwordError.style.display = "none"
})