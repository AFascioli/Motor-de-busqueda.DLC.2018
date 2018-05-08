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
                

            </div>

            <div id="tablaResultado"> 
                
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
                    
                    
                
            </div>
            <footer>
                Dise&nacute;o de Lenguajes de Consulta, 2018
                Autores: Ponce Diego, Bargiano Florencia y Fascioli Agust&iacute;n
            </footer> 
        </div>
    </body>
</html>

