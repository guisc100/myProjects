import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.Period;


class Pessoa{   // 1– Classe Pessoa com os atributos: nome (String) e data nascimento (LocalDate).
    protected String nome;
    protected LocalDate dataNascimento;

    public Pessoa(String nome, String dataNascimento) {   
        this.nome = nome;
        this.dataNascimento = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
    }
}

class Funcionario extends Pessoa{ // 2 - Classe Funcionário que estenda a classe Pessoa, com os atributos: salário (BigDecimal) e função (String).
    public BigDecimal salario;
    public String funcao;

    public Funcionario(String nome, String dataNascimento, String salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = new BigDecimal(salario.replace(",", "."));

        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return String.format("Nome: %s, Nascimento: %s, Salário: %s, Função: %s", // • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
                nome, 
                dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), // • informação de data deve ser exibido no formato dd/mm/aaaa;
                df.format(salario), funcao); 
    }
}


public class Principal { // 3 – Deve conter uma classe Principal para executar as seguintes ações:
    public static void main(String[] args) {
        List <Funcionario> funcionarios = new ArrayList<>();

        // 3.1 Inserir funcionários
        funcionarios.add(new Funcionario("MARIA", "18/10/2000", "2009.44", "OPERADOR"));
        funcionarios.add(new Funcionario("JOAO", "12/05/1990", "2284.38", "OPERADOR"));
        funcionarios.add(new Funcionario("CAIO", "02/05/1961", "9836.14", "COORDENADOR"));
        funcionarios.add(new Funcionario("MIGUEL", "14/10/1988", "19119.88", "DIRETOR"));
        funcionarios.add(new Funcionario("ALICE", "05/01/1995", "2234.68", "RECEPCIONISTA"));
        funcionarios.add(new Funcionario("HEITOR", "19/11/1999", "1582.72", "OPERADOR"));
        funcionarios.add(new Funcionario("ARTHUR", "31/03/1993", "4071.84", "CONTADOR"));
        funcionarios.add(new Funcionario("LAURA", "08/07/1994", "3017.45", "GERENTE"));
        funcionarios.add(new Funcionario("HELOISA", "24/05/2003", "1606.85", "ELETRICISTA"));
        funcionarios.add(new Funcionario("HELENA", "02/09/1996", "2799.93", "GERENTE"));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.nome.equals("JOAO"));

        // 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
        System.out.println("Funcionários:");
        funcionarios.forEach(System.out::println);

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarios.forEach(funcionario -> funcionario.salario = funcionario.salario.multiply(new BigDecimal("1.1")));  // aumentar 10% = 1.1

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> //lista de funcionarios (valor)
        funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao)); // funcao como fk

        // 3.6 – Imprimir os funcionários, agrupados por função.
        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(System.out::println);
        });

        // 3.8 Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("\nFuncionários que fazem aniversário em outubro (10) e dezembro (12):");
        funcionarios.stream()
        .filter(funcionario -> funcionario.dataNascimento.getMonthValue() == 10 || funcionario.dataNascimento.getMonthValue() == 12)
        .forEach(System.out::println);

     // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        System.out.println("\nFuncionário com maior idade:");
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(funcionario -> Period.between(funcionario.dataNascimento, LocalDate.now()).getYears())); //Comparando com dn e dia atual.
        System.out.println("Nome: " + maisVelho.nome +";" + "\nIdade: " + Period.between(maisVelho.dataNascimento, LocalDate.now()).getYears());

        // 3.10 Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(funcionario -> funcionario.nome))
                .forEach(System.out::println);

        // 3.11 Imprimir o total dos salários dos funcionários
        System.out.println("\nTotal dos salários dos funcionários: " + funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\nSalários em termos de salários mínimos:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> System.out.println(funcionario.nome + ": " + funcionario.salario.divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP)));
    }
}
