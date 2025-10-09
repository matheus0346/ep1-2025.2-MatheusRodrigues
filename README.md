# 🏥 Trabalho Prático – Sistema de Gerenciamento Hospitalar  

### 🎯 Objetivo  
Implementar um *Sistema de Gerenciamento Hospitalar* em *Java, aplicando conceitos avançados de **Programação Orientada a Objetos (POO), com foco em **herança, polimorfismo, encapsulamento, persistência de dados* e *regras de negócio mais complexas*.  

---
## Descrição do Projeto

Desenvolvimento de um sistema de gerenciamento hospitalar utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

## Dados do Aluno

- **Nome completo:** Matheus Rodrigues Pontes
- **Matrícula:** 242024478
- **Curso:** Engenharias
- **Turma:** 02

---

## Instruções para Compilação e Execução

1. **Compilação:**  
   javac -d out src/main/java/com/gestaohospitalar/model/*.java src/main/java/com/gestaohospitalar/repository/*.java src/main/java/com/gestaohospitalar/*.java

2. **Execução:**  
   java -cp out src.main.java.com.gestaohospitalar.Main

3. **Estrutura de Pastas:**  
   src/main/java/com/gestaohospitalar/: Pasta base do projeto.

   model/: Contém as classes de entidade (Paciente, Medico, Consulta, etc.).

   repository/: Contém as classes responsáveis pela persistência de dados em arquivos .csv.

   out/: Pasta onde os arquivos compilados (.class) são gerados.

4. **Versão do JAVA utilizada:**  
   Java 25 (LTS)

---

## Vídeo de Demonstração

- [Inserir o link para o vídeo no YouTube/Drive aqui]

---

## Prints da Execução

1. Menu Principal:  
   <img width="271" height="182" alt="image" src="https://github.com/user-attachments/assets/7cd67a27-3912-497a-a8e9-f53c144cd3fc" />


2. Cadastro de Médico:  
   <img width="308" height="220" alt="image" src="https://github.com/user-attachments/assets/5576de4f-ad03-4bc3-8deb-41012fd5b928" />


3. Relatório de Médicos com Agenda e Desempenho:  
   <img width="503" height="476" alt="image" src="https://github.com/user-attachments/assets/5b532ea1-c0f9-4358-992f-1c7f6f361672" />


---

---

## Observações (Extras ou Dificuldades)

### Explicação da Modelagem (Estrutura de Classes)

O sistema foi modelado utilizando os princípios da Orientação a Objetos para criar uma estrutura coesa e extensível.

-   **`Pessoa` (Abstrata):** Superclasse que centraliza atributos comuns a `Paciente` e `Medico`, como nome, CPF e dados de contato. Ela também contém a lógica para calcular a idade automaticamente a partir da data de nascimento.
-   **`Paciente` e `Medico`:** Herdam de `Pessoa` e adicionam atributos e comportamentos específicos (ex: `crm` para Médico, `numeroProntuario` e histórico para Paciente).
-   **`Consulta` e `Internacao`:** Classes centrais que associam Pacientes, Médicos e a infraestrutura do hospital (`Quarto`), contendo as principais regras de negócio, como cálculo de custos com descontos e validação de conflitos.
-   **Classes de Suporte e Enums:** Classes como `PlanoSaude`, `Especialidade` e `Quarto` modelam as entidades do sistema. O uso de `Enums` (como `Prioridade`, `StatusConsulta`, `TipoQuarto`) garante a integridade e consistência dos dados, evitando erros de digitação e tornando o código mais legível.
-   **Repositórios (Padrão DAO):** Para cada entidade principal, uma classe `Repository` (`PacienteRepository`, `MedicoRepository`, etc.) foi criada para encapsular a lógica de persistência de dados em arquivos `.csv`, separando as responsabilidades de negócio e de acesso a dados.

---

## Contato

- Email - matheusrodriguespontes346@gmail.com

---

### 🖥️ Descrição do Sistema  

O sistema deve simular o funcionamento de um hospital com cadastro de *pacientes, médicos, especialidades, consultas e internações*.  

1. *Cadastro de Pacientes*  
   - Pacientes comuns e pacientes especiais (ex: com plano de saúde).  
   - Cada paciente deve ter: nome, CPF, idade, histórico de consultas e internações.  

2. *Cadastro de Médicos*  
   - Médicos podem ter especialidades (ex: cardiologia, pediatria, ortopedia).  
   - Cada médico deve ter: nome, CRM, especialidade, custo da consulta e agenda de horários.  

3. *Agendamento de Consultas*  
   - Um paciente pode agendar uma consulta com um médico disponível.  
   - Consultas devem registrar: paciente, médico, data/hora, local, status (agendada, concluída, cancelada).  
   - Pacientes especiais (plano de saúde) podem ter *vantagens*, como desconto.  
   - Duas consultas não podem estar agendadas com o mesmo médico na mesma hora, ou no mesmo local e hora

4. *Consultas e Diagnósticos*  
   - Ao concluir uma consulta, o médico pode registrar *diagnóstico* e/ou *prescrição de medicamentos*.  
   - Cada consulta deve ser registrada no *histórico do paciente*.  

5. *Internações*  
   - Pacientes podem ser internados.  
   - Registrar: paciente, médico responsável, data de entrada, data de saída (se já liberado), quarto e custo da internação.  
   - Deve existir controle de *ocupação dos quartos* (não permitir duas internações no mesmo quarto simultaneamente).  
   - Internações devem poder ser canceladas, quando isso ocorrer, o sistema deve ser atualizado automaticamente.

6. *Planos de saúde*    
   -  Planos de saude podem ser cadastrados.
   -  Cada plano pode oferecer *descontos* para *especializações* diferentes, com possibilidade de descontos variados.
   -  Um paciente que tenha o plano de saúde deve ter o desconto aplicado.
   -  Deve existir a possibilidade de um plano *especial* que torna internação de menos de uma semana de duração gratuita.
   -  Pacientes com 60+ anos de idade devem ter descontos diferentes.

7. *Relatórios*  
   - Pacientes cadastrados (com histórico de consultas e internações).  
   - Médicos cadastrados (com agenda e número de consultas realizadas).  
   - Consultas futuras e passadas (com filtros por paciente, médico ou especialidade).  
   - Pacientes internados no momento (com tempo de internação).  
   - Estatísticas gerais (ex: médico que mais atendeu, especialidade mais procurada).  
   - Quantidade de pessoas em um determinado plano de saúde e quanto aquele plano *economizou* das pessoas que o usam.  


---

### ⚙️ Requisitos Técnicos  
- O sistema deve ser implementado em *Java*.  
- Interface via *terminal (linha de comando)*.  
- Os dados devem ser persistidos em *arquivos* (.txt ou .csv).  
- Deve existir *menu interativo*, permitindo navegar entre as opções principais.  

---

### 📊 Critérios de Avaliação  

1. *Modos da Aplicação (1,5)* → Cadastro de pacientes, médicos, planos de saúde, consultas e internações.  
2. *Armazenamento em arquivo (1,0)* → Dados persistidos corretamente, leitura e escrita funcional.  
3. *Herança (1,0)* → Ex.: Paciente e PacienteEspecial, Consulta e ConsultaEspecial, Médico e subclasses por especialidade.  
4. *Polimorfismo (1,0)* → Ex.: regras diferentes para agendamento, preços de consultas.
5. *Encapsulamento (1,0)* → Atributos privados, getters e setters adequados.  
6. *Modelagem (1,0)* → Estrutura de classes clara, bem planejada e com relacionamentos consistentes.  
7. *Execução (0,5)* → Sistema compila, roda sem erros e possui menus funcionais.  
8. *Qualidade do Código (1,0)* → Código limpo, organizado, nomes adequados e boas práticas.  
9. *Repositório (1,0)* → Uso adequado de versionamento, commits frequentes com mensagens claras.  
10. *README (1,0)* → Vídeo curto (máx. 5 min) demonstrando as funcionalidades + prints de execução + explicação da modelagem.  

🔹 *Total = 10 pontos*  
🔹 *Pontuação extra (até 1,5)* → Melhorias relevantes, como:  
- Sistema de triagem automática com fila de prioridade.  
- Estatísticas avançadas (tempo médio de internação, taxa de ocupação por especialidade).  
- Exportação de relatórios em formato .csv ou .pdf.  
- Implementação de testes unitários para classes principais.  
- Menu visual.
