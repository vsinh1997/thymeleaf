const passwordVal = document.getElementById("password")
const passwordError = document.getElementById("passwordErr")
passwordVal.addEventListener("keyup", function () {
    passwordError.style.display = "none"
})