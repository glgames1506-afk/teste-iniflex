import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe principal para execução das ações solicitadas no teste prático.
 */
public class Principal {
    public static void main(String[] args) {
        // 3.1 – Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 18), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Remover o funcionário “João” da lista
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // Formatadores para atender ao requisito 3.3
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numFmt = NumberFormat.getInstance(new Locale("pt", "BR"));
        numFmt.setMinimumFractionDigits(2);
        numFmt.setMaximumFractionDigits(2);

        // 3.3 – Imprimir todos os funcionários com todas suas informações
        System.out.println("--- LISTA DE FUNCIONÁRIOS ---");
        System.out.printf("%-10s | %-12s | %-12s | %-15s%n", "Nome", "Nascimento", "Salário", "Função");
        for (Funcionario f : funcionarios) {
            System.out.printf("%-10s | %-12s | %-12s | %-15s%n", 
                f.getNome(), 
                f.getDataNascimento().format(dateFmt), 
                numFmt.format(f.getSalario()), 
                f.getFuncao());
        }

        // 3.4 – Aumento de 10% de salário
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario);
        }

        // 3.5 – Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários, agrupados por função
        System.out.println("\n--- FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ---");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println("   - " + f.getNome()));
        });

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\n--- ANIVERSARIANTES DOS MESES 10 E 12 ---");
        funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .forEach(f -> System.out.println(f.getNome() + ": " + f.getDataNascimento().format(dateFmt)));

        // 3.9 – Imprimir o funcionário com a maior idade
        System.out.println("\n--- FUNCIONÁRIO COM MAIOR IDADE ---");
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(f -> f.getDataNascimento()));
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n--- LISTA EM ORDEM ALFABÉTICA ---");
        funcionarios.stream()
            .sorted(Comparator.comparing(Pessoa::getNome))
            .forEach(f -> System.out.println(f.getNome()));

        // 3.11 – Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTOTAL DOS SALÁRIOS: R$ " + numFmt.format(totalSalarios));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário (Mínimo: R$ 1212.00)
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n--- SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ---");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + qtdMinimos + " salários mínimos.");
        }
    }
}
