package src.main.java.com.gestaohospitalar;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import src.main.java.com.gestaohospitalar.model.*;
import src.main.java.com.gestaohospitalar.repository.*;

public class Main {

    private static final PacienteRepository pacienteRepository = new PacienteRepository();
    private static final MedicoRepository medicoRepository = new MedicoRepository();
    private static final EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();
    private static final ConsultaRepository consultaRepository = new ConsultaRepository();
    private static final QuartoRepository quartoRepository = new QuartoRepository();
    private static final InternacaoRepository internacaoRepository = new InternacaoRepository();

    private static List<PlanoSaude> planosDisponiveis = new ArrayList<>();
    private static List<Especialidade> especialidades;
    private static List<Paciente> pacientes;
    private static List<Medico> medicos;
    private static List<Consulta> consultas;
    private static List<Quarto> quartos;
    private static List<Internacao> internacoes;

    public static void main(String[] args) {
        carregarDadosIniciais();

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE GERENCIAMENTO HOSPITALAR");
            System.out.println("========================================");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Médicos");
            System.out.println("3. Gerenciar Consultas");
            System.out.println("4. Gerenciar Infraestrutura (Quartos)");
            System.out.println("5. Gerenciar Internações");
            System.out.println("6. Gerenciar Especialidades");
            System.out.println("7. Emitir Relatórios");
            System.out.println("0. Sair e Salvar");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
            } catch (Exception e) {
                opcao = -1;
            }
            scanner.nextLine();

            switch (opcao) {
                case 1: gerenciarPacientes(scanner); break;
                case 2: gerenciarMedicos(scanner); break;
                case 3: gerenciarConsultas(scanner); break;
                case 4: gerenciarInfraestrutura(scanner); break;
                case 5: gerenciarInternacoes(scanner); break;
                case 6: gerenciarEspecialidades(scanner); break;
                case 7: gerenciarRelatorios(scanner); break;
                case 0: System.out.println("Salvando todos os dados. Saindo do sistema..."); break;
                default: System.out.println("Opção inválida! Por favor, tente novamente."); break;
            }
        }
        scanner.close();
    }

    private static void carregarDadosIniciais() {
        planosDisponiveis.add(new PlanoSaude("Platinum", 0.3, true));
        planosDisponiveis.add(new PlanoSaude("Gold", 0.15, false));
        planosDisponiveis.add(new PlanoSaude("Basic", 0.05, false));

        especialidades = especialidadeRepository.carregar();
        pacientes = pacienteRepository.carregar(planosDisponiveis);
        medicos = medicoRepository.carregar(especialidades);
        consultas = consultaRepository.carregar(pacientes, medicos);
        quartos = quartoRepository.carregar();
        internacoes = internacaoRepository.carregar(pacientes, medicos, quartos);
        System.out.println("Dados carregados com sucesso!");
    }

    // --- SUBMENUS
    private static void gerenciarPacientes(Scanner scanner) {
        int opcao = -1;
        while(opcao != 0){
            System.out.println("\n--- Gerenciar Pacientes ---");
            System.out.println("1. Cadastrar Novo Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Atualizar Dados do Paciente"); // <-- NOVO
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
            } catch (Exception e) {
                opcao = -1;
            }
            scanner.nextLine();

            switch(opcao){
                case 1:
                    cadastrarPaciente(scanner);
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    atualizarPaciente(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    private static void cadastrarPaciente(Scanner scanner) {
        
        try {
            System.out.println("\n--- Cadastro de Novo Paciente ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Data de Nascimento (AAAA-MM-DD): ");
            LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Número do Prontuário: ");
            String numProntuario = scanner.nextLine();
            System.out.print("Tipo Sanguíneo: ");
            String tipoSanguineo = scanner.nextLine();

            System.out.println("Escolha a Prioridade:");
            for (Prioridade p : Prioridade.values()) {
                System.out.println(p.getCodigo() + " - " + p.getDescricao());
            }
            System.out.print("Digite o código da prioridade: ");
            int codigoPrioridade = scanner.nextInt();
            scanner.nextLine();
            Prioridade prioridade = Prioridade.valueOfCodigo(codigoPrioridade);

            System.out.println("Planos Disponíveis: Platinum, Gold, Basic, NENHUM");
            System.out.print("Escolha o Plano de Saúde: ");
            String nomePlano = scanner.nextLine();
            PlanoSaude planoSaude = planosDisponiveis.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nomePlano))
                .findFirst().orElse(null);

            System.out.print("Alergias (separadas por vírgula): ");
            String alergiasInput = scanner.nextLine();
            List<String> alergias = alergiasInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(alergiasInput.split(",")));

            System.out.print("Medicamentos em Uso (separados por vírgula): ");
            String medicamentosInput = scanner.nextLine();
            List<String> medicamentos = medicamentosInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(medicamentosInput.split(",")));

            Paciente novoPaciente = new Paciente(nome, cpf, dataNascimento, endereco, telefone, email,
                    numProntuario, tipoSanguineo, prioridade, planoSaude, alergias, medicamentos);
            
            pacientes.add(novoPaciente);
            pacienteRepository.salvar(pacientes, planosDisponiveis);
            System.out.println("Paciente cadastrado com sucesso! Idade calculada: " + novoPaciente.getIdade() + " anos.");

        } catch (DateTimeParseException e) {
            System.err.println("Erro: Formato de data inválido! Use AAAA-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    private static void listarPacientes() {
        System.out.println("\n--- Lista de Pacientes ---");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Paciente p : pacientes) {
                System.out.println("----------------------------------------");
                System.out.println("Nome: " + p.getNome() + " | Idade: " + p.getIdade() + " | CPF: " + p.getCpf());
                System.out.println("Prioridade: " + p.getPrioridade().getDescricao());
                System.out.println("Plano: " + (p.getPlanoDeSaude() != null ? p.getPlanoDeSaude().getNome() : "Nenhum"));
            }
        }
    }
    private static void gerenciarMedicos(Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Médicos ---");
            System.out.println("1. Cadastrar Novo Médico");
            System.out.println("2. Listar Médicos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarMedico(scanner);
                    break;
                case 2:
                    listarMedicos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    private static void cadastrarMedico(Scanner scanner) {
        try {
            System.out.println("\n--- Cadastro de Novo Médico ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Data de Nascimento (AAAA-MM-DD): ");
            LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("CRM: ");
            String crm = scanner.nextLine();
            System.out.print("Custo da Consulta: ");
            double custo = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Especialidades Disponíveis:");
            especialidades.forEach(e -> System.out.println("- " + e.getNome()));
            System.out.print("Digite o nome da especialidade: ");
            String nomeEspecialidade = scanner.nextLine();
            Especialidade especialidade = especialidades.stream()
                    .filter(e -> e.getNome().equalsIgnoreCase(nomeEspecialidade))
                    .findFirst().orElse(null);

            if (especialidade == null) {
                System.err.println("Erro: Especialidade não encontrada! Cadastre-a primeiro.");
                return;
            }

            Medico novoMedico = new Medico(nome, cpf, dataNascimento, endereco, telefone, email, crm, especialidade, custo);
            medicos.add(novoMedico);
            medicoRepository.salvar(medicos);
            System.out.println("Médico cadastrado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar médico: " + e.getMessage());
        }
    }
    private static void listarMedicos() {
        System.out.println("\n--- Lista de Médicos ---");
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
        } else {
            for (Medico m : medicos) {
                System.out.println("--------------------");
                System.out.println("Nome: " + m.getNome() + " | CRM: " + m.getCrm());
                System.out.println("Especialidade: " + m.getEspecialidade().getNome() + " | Custo: R$" + m.getCustoDaConsulta());
            }
        }
    }
    private static void gerenciarConsultas(Scanner scanner) {
        System.out.println("\n--- Gerenciar Consultas ---");
        System.out.println("1. Agendar Nova Consulta");
        System.out.println("2. Listar Todas as Consultas");
        System.out.println("3. Concluir Consulta");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch(opcao) {
            case 1:
                agendarConsulta(scanner);
                break;
            case 2:
                listarConsultas();
                break; case 3:
                concluirConsulta(scanner);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    private static void agendarConsulta(Scanner scanner) {
        try {
            System.out.println("\n--- Agendamento de Consulta ---");
            System.out.print("CPF do Paciente: ");
            String cpfPaciente = scanner.nextLine().trim(); 
            Paciente paciente = pacientes.stream()
                .filter(p -> p.getCpf().equals(cpfPaciente))
                .findFirst().orElse(null);

            if (paciente == null) {
                System.err.println("Erro: Paciente não encontrado!");
                return; 
            }

            System.out.print("CRM do Médico: ");
            String crmMedico = scanner.nextLine().trim(); 
            Medico medico = medicos.stream()
                .filter(m -> m.getCrm().equals(crmMedico))
                .findFirst().orElse(null);

            if (medico == null) {
                System.err.println("Erro: Médico não encontrado!");
                return;
            }

            System.out.print("Data e Hora da Consulta (AAAA-MM-DDTHH:MM): "); 
            LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine().trim());

            System.out.print("Local da Consulta (ex: Consultório 101): ");
            String local = scanner.nextLine().trim();
        
            // Verifica se o médico já tem uma consulta agendada no mesmo horário
            boolean medicoOcupado = consultas.stream()
                .anyMatch(c -> c.getStatus() == StatusConsulta.AGENDADA &&
                               c.getMedico().getCrm().equals(crmMedico) &&
                               c.getDataHora().isEqual(dataHora));
        
            // Verifica se o local já está reservado no mesmo horário
            boolean localOcupado = consultas.stream()
                .anyMatch(c -> c.getStatus() == StatusConsulta.AGENDADA &&
                               c.getLocal().equalsIgnoreCase(local) &&
                               c.getDataHora().isEqual(dataHora));
        
            // Se houver qualquer conflito, exibe os erros e impede o agendamento
            if (medicoOcupado || localOcupado) {
                System.err.println("\n!!! Não foi possível agendar a consulta. Motivo(s):");
                if (medicoOcupado) {
                    System.err.println("- O médico " + medico.getNome() + " já possui um agendamento neste horário.");
                }
                if (localOcupado) {
                    System.err.println("- O local '" + local + "' já está reservado para este horário.");
                }
                return;
            }

            Consulta novaConsulta = new Consulta(paciente, medico, dataHora, local);
            consultas.add(novaConsulta);
            consultaRepository.salvar(consultas);
        
            System.out.println("\n>>> Consulta agendada com sucesso! <<<");
            System.out.println(novaConsulta.toString());

        } catch (DateTimeParseException e) {
            System.err.println("Erro de formato: A data e hora devem estar no formato AAAA-MM-DDTHH:MM.");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado ao agendar a consulta: " + e.getMessage());
        }
    }
    private static void listarConsultas() {
        System.out.println("\n--- Lista de Consultas ---");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
        } else {
            consultas.forEach(c -> System.out.println(c.toString()));
        }
    }
    private static void concluirConsulta(Scanner scanner) {
    System.out.println("\n--- Concluir Consulta ---");

    List<Consulta> consultasAgendadas = consultas.stream()
        .filter(c -> c.getStatus() == StatusConsulta.AGENDADA)
        .collect(Collectors.toList());

    if (consultasAgendadas.isEmpty()) {
        System.out.println("Não há consultas agendadas para concluir.");
        return;
    }

    System.out.println("Consultas agendadas disponíveis para conclusão:");
    for (int i = 0; i < consultasAgendadas.size(); i++) {
        Consulta c = consultasAgendadas.get(i);
        System.out.println((i + 1) + " - Paciente: " + c.getPaciente().getNome() +
                           " | Médico: " + c.getMedico().getNome() +
                           " | Data: " + c.getDataHora());
    }

    try {
        System.out.print("\nDigite o número da consulta que deseja concluir: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > consultasAgendadas.size()) {
            System.err.println("Erro: Escolha inválida.");
            return;
        }

        Consulta consultaParaConcluir = consultasAgendadas.get(escolha - 1);

        System.out.print("Digite o diagnóstico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Digite a prescrição (medicamentos, separados por vírgula): ");
        String prescricaoInput = scanner.nextLine();
        List<String> prescricao = prescricaoInput.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(prescricaoInput.split(",")));

        consultaParaConcluir.setDiagnostico(diagnostico);
        consultaParaConcluir.setPrescricao(prescricao);
        consultaParaConcluir.setStatus(StatusConsulta.CONCLUIDA);

        consultaRepository.salvar(consultas);

        System.out.println("Consulta concluída e registrada no histórico com sucesso!");

    } catch (Exception e) {
        System.err.println("Erro ao concluir consulta: entrada inválida.");
    }
}
    private static void gerenciarEspecialidades(Scanner scanner) {
    int opcao = -1;
    while (opcao != 0) {
        System.out.println("\n--- Gerenciar Especialidades ---");
        System.out.println("1. Cadastrar Nova Especialidade");
        System.out.println("2. Listar Especialidades");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        
        try {
            opcao = scanner.nextInt();
        } catch (Exception e) {
            opcao = -1;
        }
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("\nDigite o nome da nova especialidade: ");
                String nomeEspecialidade = scanner.nextLine();
                
                boolean existe = especialidades.stream()
                        .anyMatch(e -> e.getNome().equalsIgnoreCase(nomeEspecialidade));
                
                if (existe) {
                    System.out.println("Erro: Especialidade já cadastrada.");
                } else {
                    especialidades.add(new Especialidade(nomeEspecialidade));
                    especialidadeRepository.salvar(especialidades);
                    System.out.println("Especialidade '" + nomeEspecialidade + "' cadastrada com sucesso!");
                }
                break;
            
            case 2:
                System.out.println("\n--- Especialidades Cadastradas ---");
                if (especialidades.isEmpty()) {
                    System.out.println("Nenhuma especialidade cadastrada.");
                } else {
                    especialidades.forEach(e -> System.out.println("- " + e.getNome()));
                }
                break;
            
            case 0:
                break;
                
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}

    // - GERENCIAR INFRAESTRUTURA (QUARTOS) -
    private static void gerenciarInfraestrutura(Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Infraestrutura ---");
            System.out.println("1. Cadastrar Novo Quarto");
            System.out.println("2. Listar Todos os Quartos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1: cadastrarQuarto(scanner); break;
                case 2: listarQuartos(); break;
                case 0: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private static void cadastrarQuarto(Scanner scanner) {
        try {
            System.out.println("\n--- Cadastro de Novo Quarto ---");
            System.out.print("Número do Quarto: ");
            int numero = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Escolha o Tipo de Quarto:");
            for (TipoQuarto t : TipoQuarto.values()) {
                System.out.println("- " + t.name());
            }
            System.out.print("Tipo: ");
            TipoQuarto tipo = TipoQuarto.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Custo da Diária (ex: 450.00): ");
            double custo = scanner.nextDouble();
            scanner.nextLine();

            Quarto novoQuarto = new Quarto(numero, tipo, custo);
            quartos.add(novoQuarto);
            quartoRepository.salvar(quartos);
            System.out.println("Quarto cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar quarto: " + e.getMessage());
        }
    }

    private static void listarQuartos() {
        System.out.println("\n--- Lista de Quartos ---");
        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
        } else {
            for (Quarto q : quartos) {
                System.out.println(q.toString());
            }
        }
    }

    // --- GERENCIAR INTERNAÇÕES ---
    private static void gerenciarInternacoes(Scanner scanner) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Internações ---");
            System.out.println("1. Realizar Nova Internação");
            System.out.println("2. Dar Alta a Paciente");
            System.out.println("3. Listar Pacientes Internados");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1: realizarInternacao(scanner); break;
                case 2: darAltaPaciente(scanner); break;
                case 3: listarInternados(); break;
                case 0: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private static void realizarInternacao(Scanner scanner) {
        try {
            System.out.println("\n--- Nova Internação ---");
            System.out.print("CPF do Paciente a ser internado: ");
            String cpfPaciente = scanner.nextLine();
            Paciente paciente = pacientes.stream().filter(p -> p.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
            if (paciente == null) {
                System.err.println("Erro: Paciente não encontrado.");
                return;
            }

            System.out.print("CRM do Médico responsável: ");
            String crmMedico = scanner.nextLine();
            Medico medico = medicos.stream().filter(m -> m.getCrm().equals(crmMedico)).findFirst().orElse(null);
            if (medico == null) {
                System.err.println("Erro: Médico não encontrado.");
                return;
            }

            System.out.println("Quartos Livres:");
            List<Quarto> quartosLivres = quartos.stream().filter(Quarto::estaLivre).collect(Collectors.toList());
            if (quartosLivres.isEmpty()) {
                System.err.println("Nenhum quarto livre no momento.");
                return;
            }
            quartosLivres.forEach(q -> System.out.println("- Quarto " + q.getNumero() + " (" + q.getTipo().name() + ")"));
            
            System.out.print("Digite o número do quarto escolhido: ");
            int numQuarto = scanner.nextInt();
            scanner.nextLine();
            Quarto quarto = quartosLivres.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);
            if (quarto == null) {
                System.err.println("Erro: Quarto inválido ou já ocupado.");
                return;
            }

            Internacao novaInternacao = new Internacao(paciente, medico, quarto);
            internacoes.add(novaInternacao);
            quartoRepository.salvar(quartos);
            internacaoRepository.salvar(internacoes);

            System.out.println("Internação realizada com sucesso!");
            System.out.println(novaInternacao.toString());

        } catch (Exception e) {
            System.err.println("Erro ao realizar internação: " + e.getMessage());
        }
    }

    private static void darAltaPaciente(Scanner scanner) {
        System.out.println("\n--- Dar Alta a Paciente ---");
        System.out.print("CPF do Paciente que receberá alta: ");
        String cpf = scanner.nextLine();

        Internacao internacaoAtiva = internacoes.stream()
            .filter(i -> i.getPaciente().getCpf().equals(cpf) && i.getStatus() == StatusInternacao.ATIVA)
            .findFirst()
            .orElse(null);

        if (internacaoAtiva == null) {
            System.err.println("Erro: Não foi encontrada uma internação ativa para este paciente.");
            return;
        }

        internacaoAtiva.darAlta();
        quartoRepository.salvar(quartos);
        internacaoRepository.salvar(internacoes);
    }

    private static void listarInternados() {
        System.out.println("\n--- Pacientes Internados Atualmente ---");
        List<Internacao> ativas = internacoes.stream()
            .filter(i -> i.getStatus() == StatusInternacao.ATIVA)
            .collect(Collectors.toList());

        if (ativas.isEmpty()) {
            System.out.println("Nenhum paciente internado no momento.");
        } else {
            for (Internacao i : ativas) {
                System.out.println("--------------------");
                System.out.println("Paciente: " + i.getPaciente().getNome() + " (CPF: " + i.getPaciente().getCpf() + ")");
                System.out.println("Quarto: " + i.getQuarto().getNumero() + " | Duração: " + i.getDuracaoEmDias() + " dias");
                System.out.println("Médico: " + i.getMedicoResponsavel().getNome());
            }
        }
    }
    private static void gerenciarRelatorios(Scanner scanner) {
    int opcao = -1;
    while(opcao != 0) {
        System.out.println("\n--- Central de Relatórios ---");
        System.out.println("1. Relatório de Pacientes com Histórico");
        System.out.println("2. Relatório de Médicos com Agenda e Desempenho");
        System.out.println("3. Relatório de Consultas com Filtros");
        System.out.println("4. Estatísticas Gerais do Hospital");
        System.out.println("5. Relatório de Desempenho dos Planos de Saúde"); // <-- NOVO
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = scanner.nextInt();
        } catch (Exception e) {
            opcao = -1;
        }
        scanner.nextLine();

        switch(opcao) {
            case 1:
                emitirRelatorioPacientesComHistorico();
                break;
            case 2:
                emitirRelatorioMedicos();
                break;
            case 3:
                emitirRelatorioConsultasComFiltro(scanner);
                break;
            case 4:
                emitirEstatisticasGerais();
                break;
            case 5:
                emitirRelatorioPlanosDeSaude(); // <-- FUNCIONALIDADE ATIVADA
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}
    private static void emitirRelatorioPacientesComHistorico() {
        System.out.println("\n==============================================");
        System.out.println("     RELATÓRIO DE PACIENTES COM HISTÓRICO");
        System.out.println("==============================================");

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente paciente : pacientes) {
            System.out.println("\n----------------------------------------------");
            System.out.println("Paciente: " + paciente.getNome() + " (CPF: " + paciente.getCpf() + ")");
            System.out.println("Idade: " + paciente.getIdade() + " | Tipo Sanguíneo: " + paciente.getTipoSanguineo());
            System.out.println("Plano de Saúde: " + (paciente.getPlanoDeSaude() != null ? paciente.getPlanoDeSaude().getNome() : "Nenhum"));

            // --- Histórico de Consultas ---
            System.out.println("\n  --- Histórico de Consultas ---");
            List<Consulta> consultasDoPaciente = consultas.stream()
                .filter(c -> c.getPaciente().getCpf().equals(paciente.getCpf()))
                .collect(Collectors.toList());
            
            if (consultasDoPaciente.isEmpty()) {
                System.out.println("    Nenhuma consulta registrada.");
            } else {
                for (Consulta consulta : consultasDoPaciente) {
                    System.out.println("    - Data: " + consulta.getDataHora().toLocalDate() + 
                                       " | Médico: " + consulta.getMedico().getNome() + 
                                       " (" + consulta.getMedico().getEspecialidade().getNome() + ")" +
                                       " | Status: " + consulta.getStatus());
                }
            }

            // --- Histórico de Internações ---
            System.out.println("\n  --- Histórico de Internações ---");
            List<Internacao> internacoesDoPaciente = internacoes.stream()
                .filter(i -> i.getPaciente().getCpf().equals(paciente.getCpf()))
                .collect(Collectors.toList());

            if (internacoesDoPaciente.isEmpty()) {
                System.out.println("    Nenhuma internação registrada.");
            } else {
                for (Internacao internacao : internacoesDoPaciente) {
                    String dataSaida = internacao.getDataSaida() != null ? internacao.getDataSaida().toLocalDate().toString() : "ATIVA";
                    System.out.println("    - Quarto: " + internacao.getQuarto().getNumero() +
                                       " | Entrada: " + internacao.getDataEntrada().toLocalDate() +
                                       " | Saída: " + dataSaida +
                                       " | Status: " + internacao.getStatus());
                }
            }
        }
        System.out.println("\n--- Fim do Relatório ---");
    }

    private static void emitirRelatorioMedicos() {
        System.out.println("\n=======================================================");
        System.out.println("     RELATÓRIO DE MÉDICOS, AGENDA E DESEMPENHO");
    System.out.println("=======================================================");

        if (medicos.isEmpty()) {
        System.out.println("Nenhum médico cadastrado.");
        return;
        }

        for (Medico medico : medicos) {
            System.out.println("\n-------------------------------------------------------");
            System.out.println("Médico: " + medico.getNome() + " (CRM: " + medico.getCrm() + ")");
            System.out.println("Especialidade: " + medico.getEspecialidade().getNome());

        // --- Filtrar as consultas apenas para este médico ---
            List<Consulta> consultasDoMedico = consultas.stream()
            .filter(c -> c.getMedico().getCrm().equals(medico.getCrm()))
            .collect(Collectors.toList());

        // --- Contar consultas concluídas (Desempenho) ---
            long consultasRealizadas = consultasDoMedico.stream()
                .filter(c -> c.getStatus() == StatusConsulta.CONCLUIDA)
                .count();
            System.out.println("Total de Consultas Realizadas: " + consultasRealizadas);

        // --- Mostrar agenda de consultas futuras ---
            System.out.println("\n  --- Agenda de Consultas Futuras (Agendadas) ---");
            List<Consulta> agenda = consultasDoMedico.stream()
                .filter(c -> c.getStatus() == StatusConsulta.AGENDADA)
                .collect(Collectors.toList());
        
            if (agenda.isEmpty()) {
                System.out.println("    Nenhuma consulta agendada no momento.");
            } else {
                for (Consulta consulta : agenda) {
                    System.out.println("    - Data: " + consulta.getDataHora() +
                                   " | Paciente: " + consulta.getPaciente().getNome() +
                                   " | Local: " + consulta.getLocal());
                }
            }
        }
        System.out.println("\n--- Fim do Relatório ---");
    }
        private static void emitirEstatisticasGerais() {
            System.out.println("\n==============================================");
            System.out.println("           ESTATÍSTICAS GERAIS");
            System.out.println("==============================================");

    // Filtra apenas as consultas que foram concluídas para as estatísticas
            List<Consulta> consultasConcluidas = consultas.stream()
                .filter(c -> c.getStatus() == StatusConsulta.CONCLUIDA)
                .collect(Collectors.toList());

            if (consultasConcluidas.isEmpty()) {
                System.out.println("Nenhuma consulta foi concluída ainda para gerar estatísticas.");
                return;
            }

    // --- 1. Médico que mais atendeu ---
    // Agrupa as consultas por médico e conta quantas cada um tem
            java.util.Map<Medico, Long> consultasPorMedico = consultasConcluidas.stream()
                .collect(Collectors.groupingBy(Consulta::getMedico, Collectors.counting()));

    // Encontra a entrada (médico + contagem) com o maior valor
            java.util.Map.Entry<Medico, Long> medicoMaisAtendeu = consultasPorMedico.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .orElse(null);

            if (medicoMaisAtendeu != null) {
                System.out.println("\n--- Médico com Mais Atendimentos ---");
                System.out.println("Nome: " + medicoMaisAtendeu.getKey().getNome());
                System.out.println("Especialidade: " + medicoMaisAtendeu.getKey().getEspecialidade().getNome());
                System.out.println("Total de Consultas Concluídas: " + medicoMaisAtendeu.getValue());
            }

    // --- 2. Especialidade mais procurada ---
    // Agrupa as consultas pela especialidade do médico e conta
            java.util.Map<Especialidade, Long> consultasPorEspecialidade = consultasConcluidas.stream()
                .collect(Collectors.groupingBy(c -> c.getMedico().getEspecialidade(), Collectors.counting()));

    // Encontra a especialidade com a maior contagem
            java.util.Map.Entry<Especialidade, Long> especialidadeMaisProcurada = consultasPorEspecialidade.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .orElse(null);

            if (especialidadeMaisProcurada != null) {
                System.out.println("\n--- Especialidade Mais Procurada ---");
                System.out.println("Nome: " + especialidadeMaisProcurada.getKey().getNome());
                System.out.println("Total de Consultas Concluídas: " + especialidadeMaisProcurada.getValue());
            }

            System.out.println("\n--- Fim das Estatísticas ---");
    }

    private static void emitirRelatorioConsultasComFiltro(Scanner scanner) {
    int opcao = -1;
    while (opcao != 0) {
        System.out.println("\n--- Relatório de Consultas ---");
        System.out.println("1. Listar Consultas Futuras (Agendadas)");
        System.out.println("2. Listar Consultas Passadas (Concluídas/Canceladas)");
        System.out.println("3. Filtrar por Paciente (CPF)");
        System.out.println("4. Filtrar por Médico (CRM)");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção de filtro: ");
        
        try {
            opcao = scanner.nextInt();
        } catch (Exception e) {
            opcao = -1;
        }
        scanner.nextLine();

        List<Consulta> resultado = new ArrayList<>();

        switch (opcao) {
            case 1:
                System.out.println("\n--- Consultas Futuras (Agendadas) ---");
                resultado = consultas.stream()
                    .filter(c -> c.getStatus() == StatusConsulta.AGENDADA && c.getDataHora().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
                imprimirListaDeConsultas(resultado);
                break;
            case 2:
                System.out.println("\n--- Consultas Passadas ---");
                resultado = consultas.stream()
                    .filter(c -> c.getStatus() != StatusConsulta.AGENDADA || c.getDataHora().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
                imprimirListaDeConsultas(resultado);
                break;
            case 3:
                System.out.print("Digite o CPF do paciente: ");
                String cpf = scanner.nextLine();
                System.out.println("\n--- Consultas do Paciente CPF: " + cpf + " ---");
                resultado = consultas.stream()
                    .filter(c -> c.getPaciente().getCpf().equals(cpf))
                    .collect(Collectors.toList());
                imprimirListaDeConsultas(resultado);
                break;
            case 4:
                System.out.print("Digite o CRM do médico: ");
                String crm = scanner.nextLine();
                System.out.println("\n--- Consultas do Médico CRM: " + crm + " ---");
                resultado = consultas.stream()
                    .filter(c -> c.getMedico().getCrm().equals(crm))
                    .collect(Collectors.toList());
                imprimirListaDeConsultas(resultado);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}

// Método auxiliar para imprimir os detalhes de uma lista de consultas
    private static void imprimirListaDeConsultas(List<Consulta> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para este filtro.");
        } else {
            for (Consulta c : lista) {
                System.out.println("--------------------");
                System.out.println("Paciente: " + c.getPaciente().getNome());
                System.out.println("Médico: " + c.getMedico().getNome() + " (" + c.getMedico().getEspecialidade().getNome() + ")");
                System.out.println("Data/Hora: " + c.getDataHora());
                System.out.println("Local: " + c.getLocal() + " | Status: " + c.getStatus());
            }
        }
    }
    private static void emitirRelatorioPlanosDeSaude() {
        System.out.println("\n=======================================================");
        System.out.println("     RELATÓRIO DE DESEMPENHO DOS PLANOS DE SAÚDE");
        System.out.println("=======================================================");

        if (planosDisponiveis.isEmpty()) {
            System.out.println("Nenhum plano de saúde cadastrado.");
            return;
        }

        for (PlanoSaude plano : planosDisponiveis) {
            System.out.println("\n-------------------------------------------------------");
            System.out.println("Plano: " + plano.getNome());

            long numeroDePacientes = pacientes.stream()
                .filter(p -> p.getPlanoDeSaude() != null && p.getPlanoDeSaude().getNome().equals(plano.getNome()))
                .count();
        
            System.out.println("Número de Pacientes Associados: " + numeroDePacientes);

            double totalEconomizado = 0.0;

            List<Consulta> consultasDoPlano = consultas.stream()
                .filter(c -> c.getPaciente().getPlanoDeSaude() != null && 
                            c.getPaciente().getPlanoDeSaude().getNome().equals(plano.getNome()))
                .collect(Collectors.toList());
        
            for (Consulta consulta : consultasDoPlano) {
                double custoBase = consulta.getMedico().getCustoDaConsulta();
                double valorPago = consulta.getValor();
                double economia = custoBase - valorPago;
                totalEconomizado += economia;
            }

            System.out.println("Total Economizado pelos Pacientes em Consultas: R$" + String.format("%.2f", totalEconomizado));
        }
        System.out.println("\n--- Fim do Relatório ---");
    }
    private static void atualizarPaciente(Scanner scanner) {
        System.out.println("\n--- Atualizar Dados do Paciente ---");
        System.out.print("Digite o CPF do paciente que deseja atualizar: ");
        String cpf = scanner.nextLine();

        Paciente pacienteParaAtualizar = pacientes.stream()
            .filter(p -> p.getCpf().equals(cpf))
            .findFirst()
            .orElse(null);

        if (pacienteParaAtualizar == null) {
            System.err.println("Erro: Paciente com o CPF " + cpf + " não encontrado.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nEditando Paciente: " + pacienteParaAtualizar.getNome());
            System.out.println("Qual informação você deseja atualizar?");
            System.out.println("1. Endereço");
            System.out.println("2. Telefone");
            System.out.println("3. Email");
            System.out.println("4. Plano de Saúde");
            System.out.println("0. Concluir Atualização");
            System.out.print("Escolha uma opção: ");
        
            try {
                opcao = scanner.nextInt();
            } catch (Exception e) {
                opcao = -1;
            }
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo endereço: ");
                    String novoEndereco = scanner.nextLine();
                    pacienteParaAtualizar.setEndereco(novoEndereco);
                    System.out.println("Endereço atualizado com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite o novo telefone: ");
                    String novoTelefone = scanner.nextLine();
                    pacienteParaAtualizar.setTelefone(novoTelefone);
                    System.out.println("Telefone atualizado com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o novo email: ");
                    String novoEmail = scanner.nextLine();
                    pacienteParaAtualizar.setEmail(novoEmail);
                    System.out.println("Email atualizado com sucesso!");
                    break;
                case 4:
                    System.out.println("Planos Disponíveis: Platinum, Gold, Basic, NENHUM");
                    System.out.print("Digite o nome do novo plano: ");
                    String nomePlano = scanner.nextLine();
                    PlanoSaude novoPlano = planosDisponiveis.stream()
                        .filter(p -> p.getNome().equalsIgnoreCase(nomePlano))
                        .findFirst().orElse(null);

                    if (novoPlano != null || nomePlano.equalsIgnoreCase("NENHUM")) {
                        pacienteParaAtualizar.setPlanoDeSaude(novoPlano);
                        System.out.println("Plano de saúde atualizado com sucesso!");
                    } else {
                        System.err.println("Erro: Plano de saúde inválido.");
                    }
                    break;
                case 0:
                    
                    pacienteRepository.salvar(pacientes, planosDisponiveis);
                    System.out.println("Atualizações salvas.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}