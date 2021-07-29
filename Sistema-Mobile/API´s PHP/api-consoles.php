<?php

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $arrayLista = array();

        mysqli_set_charset($con, 'utf8mb4');

        $sqlCommand = "SELECT * FROM tbl_produto WHERE categoria = 'Consoles'";

        //variável que funciona como o ExecuteNonQuery
        $stmt = $con->prepare($sqlCommand);
        $stmt->execute();

        $stmt->bind_result($id_prod, $nome, $descricao, $categoria, $preco, $idImagem);

        //cursor
        while($stmt->fetch()){
            $temp = [
                'id_prod' => $id_prod,
                'nome' => $nome, 
                'descricao' => $descricao,
                'categoria' => $categoria,
                'preco' => $preco,
                'idImagem' => $idImagem

            ];
            //adiciona itens recuperados ao array
            array_push($arrayLista, $temp); 
        }
        //retorna dados da lista em json, sendo possível consumir em outra aplicação
        echo json_encode($arrayLista, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);

    }
?>