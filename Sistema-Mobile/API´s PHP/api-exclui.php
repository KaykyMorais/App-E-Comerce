<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $id_item = $_POST['id_item'];

        $comandoSql = "DELETE FROM tbl_carrinho WHERE id_item = $id_item";

        if(mysqli_query($con, $comandoSql)){
            echo("true");
        }else{
            echo("false"); 
        }

    }
?>