#language: pt
@testeNovo
Funcionalidade: Realizar fluxo completo de compra num site de E-Commerce
      Esquema do Cenário: Efetuar compra de smartphone
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
        #Alteração de quantidade no carrinho
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
        Então Eu fecho o site do E-Commerce

        Exemplos:
          | ecommerceURL | cliente  | item               |
          | eletrum      | cliente1 | Samsung Galaxy     |

