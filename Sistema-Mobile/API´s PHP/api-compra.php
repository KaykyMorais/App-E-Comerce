<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $preco_total = $_POST["preco_total"];
        $nm_cliente = $_POST["nm_cliente"];
        $dt_compra = $_POST["dt_compra"];
        $hr_compra = $_POST["hr_compra"];
        $forma_pagamento = $_POST["forma_pagamento"];

        $comandoSql = "INSERT INTO tbl_compra(preco_total, nm_cliente, dt_compra, hr_compra, forma_pagamento) values ($preco_total, '$nm_cliente', '$dt_compra', '$hr_compra', '$forma_pagamento')";
        $comandoSql2 = "TRUNCATE TABLE tbl_carrinho";

        if(mysqli_query($con, $comandoSql)){
            echo("true");
        }else{
            echo("false"); 
        }

        if(mysqli_query($con, $comandoSql2)){
            echo("true");
        }else{
            echo("false"); 
        }

    }
?>