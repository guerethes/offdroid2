# OffDroid

## Instalação 
```gradle

repositories { 
     maven { url "" } 
}

dependencies { 
    compile 'br.com.guerethes.offdroid:OffDroid:@version'
}
```

## Configurando o projeto
Após importar o OffDroid, é necessário criar o arquivo **offdroid.properties** na pasta **assets**. Nesse arquivo você deve colocar obrigatoriamente a URL Base do seu projeto. Você também pode colocar informações que podem se inseridas no Headers das requisições. 
```gradle
url_base="URL BASE DO PROJETO"
```
## Mapeamento da classe
Para que sua classe seja persistida deve implementar o **PersistDB** na sua classe como mostra o exemplo abaixo.
```java
@Path("mensagens")
@Headers("Authorization")
public class Mensagem implements PersistDB {

    private Object id;

    @JsonProperty("room-name")
    private String roomName;
    
    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }
```
### Anotações para as classes
* `@Path`: Usado para definir o path do serviço que vai usar a classe;
* `@Headers`: As infomações que vão ser inseridas no headers da requisições;
* `@POST`: Nessa anotação você pode definir qual URL vai ser usada quando houver serviços de POST;
* `@GET`: Nessa anotação você pode definir qual URL vai ser usada quando houver serviços de GET;
* `@PUT`: Nessa anotação você pode definir qual URL vai ser usada quando houver serviços de PUT;
* `@Delete`: Nessa anotação você pode definir qual URL vai ser usada quando houver serviços de Delete;
* `@OnlyLocalStorage`: Com essa anotação você defini que sua classe só vai persistir localmente;
* `@OnlyOnLine`: Com essa anotação você defini que sua classe só vai ter funções online;

## Usando QueryOffDroidManager
### Consulta
```java
QueryOffDroidManager queryOffDroidManager = QueryOffDroidManager.from(clazz.class, context);
queryOffDroidManager.add(Restriction.eq("param", value, EstrategiaPath.PATH_PARAM));
queryOffDroidManager.add(Restriction.orderByAsc("param"));
queryOffDroidManager.add(Restriction.limit(value));
queryOffDroidManager.toList();
```
### Métodos de inserção, atualização e remoção
```java
QueryOffDroidManager queryOffDroidManager = QueryOffDroidManager.from(clazz.class, context);
queryOffDroidManager.insert(persistDB);
queryOffDroidManager.update(persistDB);
queryOffDroidManager.remove(persistDB);
```

## License
    The MIT License (MIT)

    Copyright (c) 2014-2016 Subhrajyoti Sen
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
