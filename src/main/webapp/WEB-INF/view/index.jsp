<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration form</title>
</head>
<body>

<h1>Java Mentor!</h1>

<h2>x -> Приветствие на супер-мега сайте </h2><br/>

<h2>${requestScope.message}</h2>

<form method='post'>
    <p><b>Введите login/password</b></p>

    <table width='100%' cellspacing='0' cellpadding='4'>
        <tr>
            <td align='right'>login</td>
            <td><input type='text' name='login' maxlength='50' size='20'></td>
        </tr>
        <tr>
            <td align='right'>Пароль</td>
            <td><input type='text' name='password' maxlength='50' size='20'></td>
        </tr>
    </table>
    <input type='submit' value='Подтвердить' name='Ok'><br>
</form>



</body>

</html>
