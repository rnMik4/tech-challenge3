# language: pt

Funcionalidade: Usuario

  Cenario: Cadastrar usuario
    Quando cadastrar um novo usuario
    Entao usuario registrado com sucesso

  Cenario: Buscar usuario
    Dado que um usuario ja foi cadastrado
    Quando efetuar a busca do usuario
    Entao usuario exibido com sucesso