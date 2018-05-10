<%-- 
    Document   : Resultado
    Created on : Apr 24, 2018, 10:14:20 AM
    Author     : dlcusr
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <title>Buscador</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="css/estilo.css">
    </head>
    <body>
        <div class="container">
            <header>
                Motor de b&uacute;squeda
            </header>

            <div id="cuerpo">
                <form method="post" action="servletconsulta2">
                    <label id="campobusqueda">
                        Ingrese texto de b&uacute;squeda: &nbsp;
                    </label>
                    <input type="text" maxlength="120" id="txt_busqueda" name ="txt_busqueda" autofocus/>
                    <input type="submit" value="Buscar" name="buscar" />
                </form>
                <form method="post" action="servletindexar">  
                    <label id="cmd_indexar">
                        Presione aqu&iacute; para indexar el nuevo archivo &nbsp;
                    </label>
                    <input type="submit" value="Indexar" name="index" />
                </form>

            </div>

            <div id="tablaResultado"> 

                <c:choose>
                    <c:when test="${resultado==null}">
                        <label>ERROR</label>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th>Nombre de documento</th>
                                <th>Peso</th>
                                <th>Frecuencia de los terminos</th>
                            </tr>

                            <c:forEach items="${resultado}" var="documento" begin="0" end="5" >
                                <tr>
                                    <td><c:out value="${documento.nombre}"> </c:out> </td>
                                    <td><c:out value="${documento.peso}"> </c:out></td>
                                    </tr>
                            </c:forEach>

                        </table>
                    </c:otherwise>
                </c:choose>

            </div>
            <footer>
                Dise&nacute;o de Lenguajes de Consulta, 2018
                Autores: Ponce Diego, Bargiano Florencia y Fascioli Agust&iacute;n
            </footer> 
        </div>
    </body>
</html>

