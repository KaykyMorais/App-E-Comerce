<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $nm_item = $_POST["nm_item"];
        $qt_item = $_POST["qt_item"];
        $preco_item = $_POST["preco_item"];
        $id_img = $_POST["id_img"];

        $comandoSql = "INSERT INTO tbl_carrinho(nm_item, qt_item, preco_item, id_img) values ('$nm_item', '$qt_item', '$preco_item', '$id_img')";

        if(mysqli_query($con, $comandoSql)){
            echo("true");
        }else{
            echo("false"); 
        }

    }
?>



    
