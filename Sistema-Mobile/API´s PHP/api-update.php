<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

    
        $qt_item = $_POST['qt_item'];
        $preco_item = $_POST['preco_item'];
        $id_item = $_POST['id_item'];

        $comandoSql = "UPDATE tbl_carrinho SET qt_item = $qt_item, preco_item = $preco_item WHERE id_item = $id_item";

        if(mysqli_query($con, $comandoSql)){
            echo("true");
        }else{
            echo("false"); 
        }

    }
?>