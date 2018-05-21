<%-- 
    Document   : documentosIndexados
    Created on : 21-may-2018, 18:17:06
    Author     : Usuario

--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <title>Buscador</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="css/estilo.css">

    </head>
    <body>
        <header class="header">
            Dise&nacute;o de Lenguajes de Consulta, 2018
        </header>

        <div id="cuerpo" style="text-align: center" >
            <label id="campobusqueda" style="font-size: 30px; ">
                Motor de b&uacute;squeda &nbsp;
            </label>
            <div class="col-md-12" style="line-height: 40px;">
                <form method="post" action="servletmotor">  
                    <input class="btn btn-md btn-primary" style="margin-right: 20%;" type="submit" value="Volver al buscador" name="index"/>
                </form>
            </div>
        </div>
        <div class="col-md-12" id="resultado">
            <div class="alert alert-success"><strong>Indexaci&oacuten correcta</strong>, se indexaron los nuevos archivos!</div>
            <%-- 
            <div style="display: block">
                <ul>
                    <c:forEach items="${listaArchivos}" var="archivo" begin="0" end="15">
                        <li><c:out value="${archivos.getName}"> </c:out></li> 
                    </c:forEach>
                </ul>
            </div>
            --%>
        </div>
    </div>

    <footer class="footer">
        Autores: Ponce Diego, Bargiano Florencia, Luzara Ezequiel y Fascioli Agust&iacute;n
    </footer> 

</body>
</html>