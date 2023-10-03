# Cadastro_De_Produto

Como usuário, quero poder cadastrar minha conta e fazer login no sistema. 
Como usuario quero consultar, adicionar, editar meus produtos.
O sistema faz aututenticação de Usuário e registra.
O sistema Registra os produtos no Banco.


+----------------------+       +------------------+  
|      Pessoa          |       |   Produto        |        
+----------------------+       +------------------+        
| -nome: String        |       | -id: int         |    
| -sobrenome: String   |       | -modelo: String  |        
| -usuario: String     |       | -marca: String   |         
| -senha: String       |       | -quantidade: int |        
| -email: String       |       | -tipo: String    |       
| -dataNascimento: Date|       | -data: Date      |       
+----------------------+       +------------------+       
      |      |                     |     |                 
      |      |                     |     |                                   
+---------------------+       +---------------------+                          
|      PessoaDAO      |       |     ProdutoDao      |                          
+---------------------+       +---------------------+                          
| +inserirPessoa()    |       | +adicionarProduto() |                          
| +excluirPessoa()    |       | +atualizarProduto() |                          
+---------------------+       | +deletarProduto()   |                          
                              | +obterTodosProdutos |                          
                              +---------------------+                          
                                      
+----------------------------------+                 
|  CadastroView                    |                  
+----------------------------------+                
| -textFieldNome: TextField        |         
| -textFieldSobrenome: TextField   |          
| -textFieldUsuario: TextField     |           
| -textFieldSenha: PasswordField   |           
| -textFieldEmail: TextField       |         
| -datePickerNascimento: DatePicker|           
| -salvar: Button                  |           
| -cancelar: Button                |           
| -excluir: Button                 |           
+----------------------------------+                 
      |                               
      |                            
+------------------------------------------+                
|  LoginView                               |                
+------------------------------------------+               
| -textFieldUsuario: TextField             |        
| -textFieldSenha: PasswordField           |                                   
| -autenticacaoService: AutenticacaoService|  
+------------------------------------------+  
		|   
 		|   
+-----------------------------+
|   AutenticacaoService       |
+-----------------------------+
| +autenticar(usuario: String |  
| senha: String): boolean     |  
+-----------------------------+
