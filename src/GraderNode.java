import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class GraderNode {

    private class NodeField {
        private Field field;

        public NodeField(Field field) {
            this.field = field;
        }

        @Override
        public String toString() {
            String modifiers = Modifier.toString(field.getModifiers()).replace(" ", "\t");

            String type = field.getType().getSimpleName();
            String name = field.getName();

            return String.format("%-16s %-16s %s", modifiers, type, name);
        }

        private String getHashString() {
            return field.getType().getSimpleName() + field.getName();
        }

        @Override
        public int hashCode() {
            return getHashString().hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof NodeField nodeField) {
                return Objects.equals(getHashString(), nodeField.getHashString());
            }
            return false;
        }
    }

    private class NodeMethod {
        private Method method;

        public NodeMethod(Method method) {
            this.method = method;
        }

        @Override
        public String toString() {
            String modifiers = Modifier.toString(method.getModifiers());
            String returnType = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            String parameterTypes = String.join(", ", Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toList());

            return String.format("%-16s %-16s %s(%s)", modifiers, returnType, methodName, parameterTypes);
        }

        private String getHashString() {
            String returnType = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            String parameterTypes = String.join(", ", Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toList());
            return returnType + methodName + parameterTypes;
        }

        @Override
        public int hashCode() {
            return getHashString().hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof NodeMethod nodeMethod) {
                return Objects.equals(getHashString(), nodeMethod.getHashString());
            }
            return false;
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String SOURCE_DIRECTORY = "./src";
    private Class<?> clazz;
    private List<GraderNode> children;

    public GraderNode(String className) {
        try {
            this.clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Discover Children
        File directory = new File(SOURCE_DIRECTORY);
        File[] files = directory.listFiles();

        // Check if the directory exists and is accessible
        if (files != null) {
            children = Arrays.stream(files)
                    .map(File::getName)
                    .map(s -> s.replaceFirst("\\.[^.]+$", ""))
                    .map(c -> {
                        try {
                            return Class.forName(c);
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(clazz::isAssignableFrom)
                    .filter(c -> (c.getSuperclass() != null && c.getSuperclass().equals(clazz)) || Arrays.stream(c.getInterfaces()).toList().contains(clazz))
                    .map(Class::getSimpleName)
                    .map(GraderNode::new)
                    .toList();
        }
    }

    private List<NodeField> getVariables() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .sorted(Comparator.comparing(Field::getName))
                .map(NodeField::new)
                .toList();
    }

    private List<NodeMethod> getMethods() {
        return Arrays.stream(clazz.getDeclaredMethods())
                .sorted(Comparator.comparing(Method::getName))
                .map(NodeMethod::new)
                .toList();
    }

    public String getIndent(int level) {
        if (level == 0) {
            return "";
        }
        return "|   ".repeat(level);
    }

    public String getIndentLeaf(int level) {
        if (level == 0) {
            return "";
        }
        return "|   ".repeat(level - 1) + "|---";
    }

    public void display() {
        display(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public void display(int level, Set<NodeField> hierarchyVariables, Set<NodeMethod> hierarchyMethods, Set<Class<?>> hierarchyInterfaces) {
        // Display Name
        System.out.print(getIndentLeaf(level));
        System.out.print(ANSI_BOLD + ANSI_UNDERLINE);
        System.out.print(clazz.getSimpleName());

        // Display Interface Implementations
        String[] interfaces = Arrays.stream(clazz.getInterfaces()).map(Class::getSimpleName).toArray(String[]::new);

        if (interfaces.length > 0 ) {
            System.out.print(" implements ");
            System.out.print(String.join(", ", interfaces));
        }

        System.out.print(ANSI_RESET);

        // Newline
        System.out.println();
        System.out.println(getIndent(level + 1));

        // Display Variables
        List<NodeField> variables = getVariables();

        if (!variables.isEmpty()) {
            for (NodeField variable : variables) {
                String colorString = "";
                if (hierarchyVariables.contains(variable)) {
                    colorString = ANSI_RED;
                } else {
                    colorString = ANSI_GREEN;
                }

                // Mark Non-Private Fields
                String variableString = variable.toString();
                variableString = variableString.replace("public ", ANSI_RED + ANSI_UNDERLINE + "public" + "\u001B[24m" + " " + colorString);
                variableString = variableString.replace("protected ", ANSI_YELLOW + ANSI_UNDERLINE + "public" + "\u001B[24m" + " " + colorString);

                System.out.print(colorString);
                System.out.println(getIndentLeaf(level + 1) + variableString);
                System.out.print(ANSI_RESET);
            }
        }

        // Newline
        System.out.println(getIndent(level + 1));

        // Display Methods
        List<NodeMethod> methods = getMethods();

        if (!methods.isEmpty()) {
            for (NodeMethod method : methods) {
                if (hierarchyMethods.contains(method)) {
                    System.out.print(ANSI_YELLOW);
                }
                System.out.println(getIndentLeaf(level + 1) + method);
                System.out.print(ANSI_RESET);
            }
        }

        // Newline
        System.out.println(getIndent(level));

        // Update Recursive Datasets
        hierarchyVariables.addAll(variables);
        hierarchyMethods.addAll(methods);
        hierarchyInterfaces.addAll(Arrays.stream(clazz.getInterfaces()).collect(Collectors.toSet()));

        // Recursively Display Subclasses
        for (GraderNode graderNode : children) {
            graderNode.display(level + 1, new HashSet<>(hierarchyVariables), new HashSet<>(hierarchyMethods), hierarchyInterfaces);
        }
    }

    @Override
    public String toString() {
        return clazz.getSimpleName();
    }

    public static void main(String[] args) {
        GraderNode e = new GraderNode("Entity");
        e.display();

        System.out.println();

        GraderNode a = new GraderNode("Action");
        a.display();
    }
}
