#language: pt
@testeNovo
Funcionalidade: Realizar fluxo completo de compra num site de E-Commerce
      Esquema do Cenário: Efetuar compra de smartphone
        Dado Que eu navego para o site do E-Commerce <ecommerceURL>
        Quando Eu realizo a busca do item <item>
        Então Eu posso ver os itens que foram encontrados
        Quando Eu seleciono um item da vitrine em Comprar
        Então Eu posso ver a página de descrição do item
        Quando Eu adiciono o item no carrinho em Comprar agora
        Então Eu estou no carrinho
        Quando Eu escolho comprar mais produtos
        Então Eu posso ver o número correto de itens no carrinho
        Quando Eu realizo a busca do item <item2>
        Então Eu posso ver os itens que foram encontrados
        Quando Eu seleciono um item da vitrine em Comprar
        Então Eu posso ver a página de descrição do item
        Quando Eu adiciono o item no carrinho em Comprar agora
        Então Eu estou no carrinho

        Exemplos:
          | ecommerceURL | cliente  | item               | item2    |
          | eletrum      | cliente1 | Samsung Galaxy     | Motorola |

