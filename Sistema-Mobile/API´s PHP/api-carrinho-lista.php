<?php

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $arrayLista = array();

        //string de comando executado no banco
        $sqlCommand = "SELECT * FROM tbl_carrinho";

        //variável que funciona como o ExecuteNonQuery
        $stmt = $con->prepare($sqlCommand);
        $stmt->execute();

        $stmt->bind_result($id_item, $nm_item, $qt_item, $preco_item, $id_img);

        //cursor
        while($stmt->fetch()){
            $temp = [
                'id_item' => $id_item,
                'nm_item' => $nm_item, 
                'qt_item' => $qt_item,
                'preco_item' => $preco_item,
                'id_img' => $id_img

            ];
            //adiciona itens recuperados ao array
            array_push($arrayLista, $temp); 
        }
        //retorna dados da lista em json, sendo possível consumir em outra aplicação
        echo json_encode($arrayLista);

    }
?>