<?php
        //metodo de consulta, os principais usados no projeto são: POST e GET
    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        //string de conexão com o banco
        $con = mysqli_connect("localhost", "root", "", "db_python") or die
        ("Conexão falhou");

        $usuario = $_GET['usuario'];

        $arrayProd = array();

        //string de comando executado no banco
        $sqlCommand = "SELECT * FROM tbl_testes WHERE usuario = '$usuario'";

        //variável que funciona como o ExecuteNonQuery
        $stmt = $con->prepare($sqlCommand);
        $stmt->execute();

        $stmt->bind_result($id_teste, $nm_jogo, $dt_jogo, $duracao, $plataforma, $usuario, $valor);

        //cursor
        while($stmt->fetch()){
            $temp = [
                'id_teste' => $id_teste,
                'nm_jogo' => $nm_jogo, 
                'dt_jogo' => $dt_jogo,
                'duracao' => $duracao,
                'plataforma' => $plataforma,
                'usuario' => $usuario,
                'valor' => $valor

            ];
            //adiciona itens recuperados ao array
            array_push($arrayProd, $temp); 
        }
        //retorna dados da lista em json, sendo possível consumir em outra aplicação
        echo json_encode($arrayProd);
    }
?>