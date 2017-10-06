Como usar:

1 - Realize o PULL do projeto

2 - Mude o caminho que o arquivo arr vai ser gerado, para realizar essa modificação siga os passos abaixo:

  2.1 - No arquivo build.gradle -> Mude o repository para um diretório válido

2 - Abra a tela de taks e selecionar a task uploadArchives, que fica dentro de upload.

3 - Para importar o offdroid no seu projeto, siga as instruções

  3.1 - Crie uma nova tag de repositório apontando para o repositório anteriormente informado.
  Ex.:
  
        
          repositories {    
                      maven { url "file:///home/victor/gradle/rep" }
          }
        
  3.2 - Para pegar a versão desejada adicione também a versão do OffDroid
  
          compile 'br.com.guerethes.offdroid:offDroidLib:0.0.1'
