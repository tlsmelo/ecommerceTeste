#language: pt
@testeNovo
Funcionalidade: Realizar fluxo completo de compra num site de E-Commerce
      Esquema do Cenário: Efetuar compra de smartphone
        Dado Que eu navego para o site do E-Commerce <ecommerceURL>
        Quando Eu realizo a busca do item <item>
        Então Eu posso ver o item <item> retornado na vitrine
        Quando Eu seleciono um item da vitrine em Comprar
        Então Eu adiciono o item no carrinho em Comprar agora
        Então Eu estou no carrinho com os valores atualizados
        #Quando Eu escolho comprar mais produtos
        #Então Eu posso ver o número correto de itens no carrinho
        Então Eu fecho o site do E-Commerce

        Exemplos:
          | ecommerceURL | cliente  | item               |
          | eletrum      | cliente1 | Samsung Galaxy     |

