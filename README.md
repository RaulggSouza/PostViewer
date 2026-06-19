# PostViewer

Aplicativo Android desenvolvido em Kotlin para exibir posts e comentários consumidos da API pública JSONPlaceholder. O projeto também permite adicionar comentários locais persistidos no dispositivo usando Room.

## Aluno e disciplina

* Nome: Raul Gonçalves Garcia de Souza
* Disciplina: PRDM

## Descrição

O PostViewer é um aplicativo Android que apresenta uma lista de posts carregados da API JSONPlaceholder. Ao selecionar um post, o usuário é direcionado para uma tela de detalhes, onde são exibidos os comentários associados ao post selecionado.

Além dos comentários vindos da API, o aplicativo permite adicionar comentários locais. Esses comentários são salvos no banco de dados local do dispositivo usando Room, associados ao ID do post, e continuam disponíveis mesmo após fechar e reabrir o aplicativo.

## Funcionalidades

* Listagem de posts consumidos da API JSONPlaceholder.
* Navegação da lista de posts para a tela de detalhes.
* Exibição dos comentários do post selecionado.
* Tratamento de estados de carregamento.
* Tratamento de erros na interface.
* Adição de comentários locais.
* Persistência de comentários locais usando Room.
* Exibição conjunta de comentários da API e comentários locais.

## Tecnologias utilizadas

* Kotlin
* Jetpack Compose
* Navigation Compose
* ViewModel
* StateFlow
* Retrofit
* Gson Converter
* Room
* KSP
* Android Studio

## Como executar o projeto

1. Clone o repositório:

```bash
git clone https://github.com/RaulggSouza/PostViewer.git
```

2. Abra o projeto no Android Studio.

3. Aguarde o Gradle Sync finalizar.

4. Execute o aplicativo em um emulador ou dispositivo físico com Android 8.0 ou superior.

## API utilizada

O projeto consome a API pública JSONPlaceholder.

Endpoints utilizados:

```text
GET https://jsonplaceholder.typicode.com/posts
GET https://jsonplaceholder.typicode.com/posts/{id}/comments
```

## Estrutura do projeto

O projeto foi organizado separando responsabilidades entre as camadas da aplicação.

```text
data/
├── local/
├── model/
├── remote/
└── repository/

ui/
├── navigation/
├── screens/
└── viewmodel/
```

### data/model

Contém as classes que representam os dados usados pelo aplicativo.

Exemplos:

* `Post`
* `Comment`

Essas classes representam os dados retornados pela API.

### data/remote

Contém a configuração de acesso à API externa.

* `JsonPlaceholderApi`: define os endpoints da API usando annotations do Retrofit.
* `ApiClient`: cria a instância do Retrofit e gera a implementação da interface da API.

### data/repository

Contém a camada responsável por centralizar o acesso aos dados.

O repository acessa a API via Retrofit e também acessa os comentários locais via Room. Dessa forma, a tela e o ViewModel não precisam saber diretamente se os dados vêm da internet ou do banco local.

### data/local

Contém a configuração do banco local usando Room.

* `LocalCommentEntity`: representa a tabela de comentários locais.
* `LocalCommentDao`: define as operações de banco, como inserir e buscar comentários.
* `PostViewerDatabase`: configura o banco Room.
* `DatabaseProvider`: cria e fornece a instância do banco local.

### ui/navigation

Contém as rotas internas do aplicativo.

As rotas foram centralizadas para evitar strings espalhadas pelo projeto e facilitar manutenção da navegação.

### ui/viewmodel

Contém os ViewModels responsáveis por controlar o estado das telas.

Os ViewModels utilizam StateFlow para expor estados como:

* carregando;
* erro;
* dados carregados;
* texto digitado no campo de novo comentário.

### ui/screens

Contém as telas criadas com Jetpack Compose.

As telas observam o estado vindo dos ViewModels e desenham a interface conforme o estado atual.

## Decisões de design

O projeto foi estruturado buscando separar responsabilidades entre interface, estado, acesso à API e persistência local.

A tela não acessa diretamente a API nem o banco de dados. Ela recebe um estado vindo do ViewModel e decide o que exibir. O ViewModel coordena as ações da tela e chama o Repository. O Repository centraliza o acesso aos dados, seja pela API externa com Retrofit ou pelo banco local com Room.

Essa separação facilita manutenção, testes e evolução do projeto. Por exemplo, se futuramente for necessário trocar a API ou alterar a forma de persistência local, a tela não precisa ser diretamente modificada.

O uso de StateFlow permite que as telas reajam automaticamente às mudanças de estado. Quando os dados são carregados, quando ocorre um erro ou quando um comentário local é adicionado, o estado é atualizado e a interface é redesenhada.

O Room foi utilizado para persistir os comentários locais no dispositivo. Cada comentário local é associado ao ID do post, permitindo que os comentários sejam exibidos apenas no post correspondente.

## Prints

Os prints do aplicativo em execução estão disponíveis na pasta `docs/`.
