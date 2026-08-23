const showPassword = (inputId, iconId) => {
    const passEle = document.getElementById(inputId);
    const iconEle = document.getElementById(iconId);
    if(passEle.getAttribute("type") === "password"){
        passEle.setAttribute("type", "text");
        iconEle.setAttribute("class", "bi bi-eye");
    }else{
        passEle.setAttribute("type", "password");
        iconEle.setAttribute("class", "bi bi-eye-slash");
    }
}