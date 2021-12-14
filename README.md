# Miniautorizador API
O MiniAutorizador disponibiliza uma API REST que permite o cadastro de cartões, sendo que todo cartão cadastro já inicia com saldo de R$ 500,00, a verificação do saldo do cartão, 
a realização de transações com um valor que será debitado do saldo do cartão.

Recursos disponíveis para acesso via API:
* **Cadastro de cartão**
* **Obter saldo do cartão**
* **Realizar transação**

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `POST` | Utilizado para criar um novo cartão. |
| `GET` | Obter saldo do cartão. |
| `POST` | Realizar uma transação. |

## Respostas

| Código | Descrição |
|---|---|
| `200` | Requisição executada com sucesso (success)..|
| `201` | Criação realizada com sucesso (success).|
| `404` | Registro pesquisado não encontrado (Not found).|
| `422` | Dados informados estão fora do escopo definido.|

# Contratos dos serviços

# Cartoes [/cartoes]

### Cadastrar (Create) [POST]

+ Attributes (object)

    + numeroCartao: número do cartão (string, required)
    + senha: senha do cartão (string, required)

+ Request (application/json)

    + Body

            {
              "numeroCartao": "6549873025634501",
              "senha": "1234"
            }

+ Response 201 (application/json)
  Criação com sucesso
    + Body

            {
                "senha": "1234",
                "numeroCartao": "6549873025634501"
            }
            
 + Response 422 (application/json)
  Caso o cartão já exista
    + Body

            {
                "senha": "1234",
                "numeroCartao": "6549873025634501"
            }
            
            
### Obter saldo (Read) [GET /{numeroCartao}]

 + Parameters
 
      + numeroCartao (required, string) ... Número do cartão
 
 + Request 
 
 + Response 200 
  Obtenção com sucesso

    + Body
    
            [saldo] 495.90
      
  + Response 404 
  Caso o cartão não exista

    + Body
    
            Sem Body
            
 # Transações [/transacoes]

### Transação (Update) [POST]

+ Request (application/json)

    + Body

            {
              "numeroCartao": "6549873025634501",
              "senhaCartao": "1234",
              "valor": 10.00
            }

+ Response 201 (application/json)
  Transação realizada com sucesso
    + Body
    
            OK
            
 + Response 422 (application/json)
  Caso alguma regra de autorização tenha barrado a mesma
    + Body
    
            SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE (dependendo da regra que impediu a autorização)
