const emailInput = document.getElementById("email")
const passwordInput = document.getElementById("password")
const emailError = document.getElementById("emailErr")
const passwordError = document.getElementById("passwordErr")

emailInput.addEventListener("keyup", () => {
    emailError.style.display = "none"
})
passwordInput.addEventListener("keyup", () => {
    passwordError.style.display = "none"
})