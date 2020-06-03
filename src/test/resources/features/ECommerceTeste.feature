#language: pt
@testeNovo
Funcionalidade: Realizar fluxo completo de compra num site de E-Commerce
      Esquema do Cenário: Simular compra de um smartphone
        Dado Que eu navego para o site do E-Commerce <ecommerceURL>
        #Realizar busca de item
        Quando Eu realizo a busca do item <item>
        Então Eu posso ver o item <item> retornado na vitrine
        #Adicionar ao carrinho
        Quando Eu seleciono um item da vitrine em Comprar
        Então Eu adiciono o item no carrinho em Comprar agora
        Então Eu estou no carrinho com os valores atualizados
        Quando Eu escolho comprar mais produtos
        Então Eu posso ver o número correto de itens no carrinho na página inicial
        Quando Eu clico no ícone do carrinho na página inicial
        Então Eu posso ver uma nova página contendo o item <item>, valor total e opções de compra
        Quando Eu clico em Finalizar Compra na mini página do carrinho
        Então Eu estou no carrinho com os valores atualizados
        #Alteração de quantidade no carrinho - Aumentar
        Quando Eu aumento a quantidade do item no carrinho
        Então Eu vejo o valor da compra atualizado
        E Eu aumento a quantidade do item no carrinho
        Então Eu vejo o valor da compra atualizado
        Quando Eu escolho comprar mais produtos
        Então Eu posso ver o número correto de itens no carrinho na página inicial
        Quando Eu clico no ícone do carrinho na página inicial
        Então Eu posso ver uma nova página contendo o item <item>, valor total e opções de compra
        Quando Eu clico em Finalizar Compra na mini página do carrinho
        Então Eu estou no carrinho com os valores atualizados
        #Alteração de quantidade no carrinho - Diminuir
        Quando Eu diminuo a quantidade do item no carrinho
        Então Eu vejo o valor da compra atualizado
        E Eu diminuo a quantidade do item no carrinho
        Então Eu vejo o valor da compra atualizado
        Quando Eu escolho comprar mais produtos
        Então Eu posso ver o número correto de itens no carrinho na página inicial
        Quando Eu clico no ícone do carrinho na página inicial
        Então Eu posso ver uma nova página contendo o item <item>, valor total e opções de compra
        Quando Eu clico em Finalizar Compra na mini página do carrinho
        Então Eu estou no carrinho com os valores atualizados
        #Remoção de item
        Quando Eu removo o item do carrinho
        Então Eu vejo a mensagem Seu carrinho está vazio
        Quando Eu clico em Escolher Produtos no carrinho
        Então Eu posso ver o número correto de itens no carrinho na página inicial
        #Realizar o fluxo total e finalizar o teste
        Quando Eu realizo a busca do item <item>
        Então Eu posso ver o item <item> retornado na vitrine
        Quando Eu seleciono um item da vitrine em Comprar
        Então Eu adiciono o item no carrinho em Comprar agora
        Então Eu estou no carrinho com os valores atualizados
        Quando Eu fecho o pedido atual
        E Eu preencho o email do cliente <cliente> e clico em continuar
        Quando Eu preencho os campos Email <cliente>, Primeiro Nome <primeiroNome>, Último Nome <ultimoNome>, CPF <cpf>, Telefone <telefone> e clico em Ir para a entrega
        E Eu preencho o campo CEP <cep>, depois o campo Número <numero> e verifico o valor do frete
        E Eu seleciono a opção cartão de crédito e preencho os campos Número do cartão <numeroCartao>, Quantidade de Parcelas <parcelas>, Nome Impresso <nomeImpresso>, Validade Mês <mes>, Validade Ano <ano>, Código de Segurança <codigoSeguranca> e CPF do Titular <cpf>
        Então Eu verifico que os valores finais da compra estão corretos
        Então Eu fecho o site do E-Commerce

        #valores cpf e cartão gerados a partir do 4devs
        Exemplos:
          | ecommerceURL | cliente  | item             | primeiroNome | ultimoNome | cpf          | telefone         | cep       | numero | numeroCartao     | parcelas  | nomeImpresso | mes | ano | codigoSeguranca |
          | eletrum      | cliente1 | Samsung Galaxy   | Teste        | QA         | 37768571886  | (11) 99999-9999  | 04571-900 | 120    | 4916524502643822 | 2         | Teste QA     | 01  | 21  | 857             |
          | eletrum      | cliente2 | Motorola         | Teste2       | QA2        | 13781700089  | (13) 99999-9999  | 04571-900 | 10     | 4916102404927658 | 1         | Teste2 QA2   | 08  | 21  | 317             |

