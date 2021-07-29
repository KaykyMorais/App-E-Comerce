<?php
        //metodo de consulta, os principais usados no projeto são: POST e GET
    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        //string de conexão com o banco
        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $nm_cliente = $_GET['nm_cliente'];

        $arrayProd = array();

        //string de comando executado no banco
        $sqlCommand = "SELECT * FROM tbl_compra WHERE nm_cliente = '$nm_cliente'";

        //variável que funciona como o ExecuteNonQuery
        $stmt = $con->prepare($sqlCommand);
        $stmt->execute();

        $stmt->bind_result($id_compra, $preco_total, $nm_cliente, $dt_compra, $hr_compra, $forma_pagamento);

        //cursor
        while($stmt->fetch()){
            $temp = [
                'id_compra' => $id_compra,
                'preco_total' => $preco_total, 
                'nm_cliente' => $nm_cliente,
                'dt_compra' => $dt_compra,
                'hr_compra' => $hr_compra,
                'forma_pagamento' => $forma_pagamento

            ];
            //adiciona itens recuperados ao array
            array_push($arrayProd, $temp); 
        }
        //retorna dados da lista em json, sendo possível consumir em outra aplicação
        echo json_encode($arrayProd);
    }
?>