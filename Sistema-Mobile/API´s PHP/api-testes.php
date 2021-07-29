<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $nm_jogo = $_POST["nm_jogo"];
        $dt_jogo = $_POST["dt_jogo"];
        $duracao = $_POST["duracao"];
        $plataforma = $_POST["plataforma"];
        $usuario = $_POST["usuario"];
        $valor = $_POST["valor"];

        $comandoSql = "INSERT INTO tbl_testes(nm_jogo, dt_jogo, duracao, plataforma, usuario, valor) values ('$nm_jogo', '$dt_jogo', '$duracao', '$plataforma', '$usuario', $valor)";

        if(mysqli_query($con, $comandoSql)){
            echo("true");
        }else{
            echo("false"); 
        }

    }
?>