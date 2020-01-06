
# Projeto Fakestore Mobile

Aplicação mobile para controle de produtos fakes.


## Estrutura do projeto

A aplicação está separado em 3 módulos / packages:

- **ui**: responsável pelos elementos visuais da aplicação e funcionalidades de interação com o usuário
- **model**: representam os modelos de objetos que são utilizados no projeto
- **service**: a camada que encapsula toda a lógica de negócio da aplicação


## Instalação

1. Instale o [Android Studio](https://developer.android.com/studio) mais recente.
2. Verifique se o git está instalado com (git -v) e, se não estiver, [baixe e instale a ferramenta](https://git-scm.com/downloads).
3. Abra o Terminal e execute: `git clone repositorio-do-projeto`.
4. Importe o projeto no Android Studio (File -> Open... ).
5. Configure um emulador Android de versão 7.1.1 (AVD Manager) ou conecte ao computador um dispositivo Android de versão semelhante.


## Executando a aplicação

1. Execute a aplicação ASP.NET, incluindo o módulo da API.
2. Edite a String **BASE_URL** inserindo IP e Porta da aplicação ASP.NET.
3. Selecione **app** no dropdown do canto superior esquerdo do Android Studio e clique em **run**.
4. Escolha em que dispositivo ou emulador você deseja que seja simulada a aplicação e clique OK.


## Se conectando com aplicação em máquina virtual

Se a aplicação Web estiver funcionando em uma máquina virtual (VirtualBox) será necessário alterar algumas configurações de rede:

1. Nas configurações (**Settings**) da máquina virtual, na aba **Network** mude o adaptador em **Attached to** para **Bridged Adapter**
2. Na mesma tela, expanda as configurações avançadas em **Advanced** e em **Promiscuous Mode** altera a opção para **Allow All**
3. Isso irá permitir que a aplicação seja acessada por qualquer máquina da rede interna além da máquina hospedeira.
4. Porem a aplicação ASP.NET funcionará apenas em localhost. Para acessá-la a partir de um IP da rede use o [issexpress-proxy](https://www.npmjs.com/package/iisexpress-proxy).
5. Para usar o iisexpress-proxy simplesmente execute o comando no terminal: `iisexpress-proxy http(s)://localhost:<porta-origem> to <porta-destino>`.
