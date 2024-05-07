package dev.annyni.view;

import dev.annyni.controller.PostController;
import dev.annyni.controller.WriterController;
import dev.annyni.dto.WriterDto;
import dev.annyni.mapper.MapperManager;
import dev.annyni.mapper.PostMapper;
import dev.annyni.mapper.WriterMapper;
import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * todo Document type WriterView
 */
@RequiredArgsConstructor
public class WriterView {

    private final WriterController writerController;
    private final PostController postController;
    private final MapperManager manager;
//    private final WriterMapper writerMapper;
//    private final PostMapper postMapper;
    private final Scanner scanner = new Scanner(System.in);

    private boolean waiting = true;

    public void printMenu(){
        while (waiting){
            System.out.println("Что бы вы хотели? Введите число.");
            System.out.println("1. Создать Writer");
            System.out.println("2. Изменить Writer");
            System.out.println("3. Найти Writer по id");
            System.out.println("4. Найти все Writers");
            System.out.println("5. Удалить Writer");
            System.out.println("6. Добавить Post в Writer");
            System.out.println("7. Выйти");

            int choice =  scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> createWriter();
                case 2 -> updateWriter();
                case 3 -> getWriter();
                case 4 -> getAllWriters();
                case 5 -> deleteWriter();
                case 6 -> {
                    System.out.println("Выход из главного меню");
                    waiting = false;
                }
                default -> System.out.println("Вы ввели неверный символ!");
            }
        }
    }

    private Post addPost(Writer writer) {
        System.out.println("Введите content Post: ");
        String content = scanner.nextLine();
        List<Label> labels = new ArrayList<>();
        Status status = Status.ACTIVE;

        Post post = Post.builder()
            .content(content)
            .created(new Date())
            .updated(new Date())
            .labels(labels)
            .status(status)
            .writer(writer)
            .build();

        System.out.println("Post успешно добален в Writer: " + writer);
        return post;
    }

    private void deleteWriter() {
        System.out.println("Введите id Writer который хотите удалить: ");

        long id = scanner.nextLong();
        writerController.delete(id);

        System.out.println("Writer удален!");

    }

    private void getAllWriters() {
        System.out.println("Представлены все Writer: ");
        List<WriterDto> writers = writerController.getAll();
        for (WriterDto writer : writers) {
            System.out.println(writer);
        }
    }

    private void getWriter() {
        System.out.println("Есть: ");
        getAllWriters();
        System.out.println("Введите id Writer которого вы хотите вывести: ");

        Long writerId = scanner.nextLong();

        Optional<WriterDto> writer = writerController.getById(writerId);

        System.out.println("Writer успешно найден! " + writer);
    }

    private void updateWriter() {
        System.out.println("Есть: ");
        getAllWriters();
        System.out.println("Введите id Writer которого вы хотите изменить: ");

        Long writerId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите новое имя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите новую фамилию: ");
        String lastName = scanner.nextLine();
        Status status = null;

        while (waiting) {
            System.out.println("Введите новый статус для Label: ");
            System.out.println("1. Writer is ACTIVE");
            System.out.println("2. Writer is UNDER_REVIEW");
            System.out.println("3. Writer is DELETE");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> status = Status.ACTIVE;
                case 2 -> status = Status.UNDER_REVIEW;
                case 3 -> status = Status.DELETED;
                case 4 -> {
                    System.out.println("Выход!");
                    waiting = false;
                }

                default -> System.out.println("Вы ввели неверный символ!");
            }
        }

        Writer writer = Writer.builder()
            .id(writerId)
            .firstName(firstName)
            .lastName(lastName)
            .status(status)
            .posts(new ArrayList<>())
            .build();

        writerController.update(manager.mapWriterToDto(writer));
        System.out.println("Writer успешно изменен! " + writer);

    }

    private void createWriter() {
        System.out.println("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String lastName = scanner.nextLine();
        Status status = Status.ACTIVE;

        Writer writer = Writer.builder()
            .firstName(firstName)
            .lastName(lastName)
            .status(status)
            .posts(new ArrayList<>())
            .build();

        writerController.create(manager.mapWriterToDto(writer));

        System.out.println("Writer успешно создан. " + writer);

        int choice = scanner.nextInt();
        System.out.println("Добавить посты Writer?");
        System.out.println("1. Да!");
        System.out.println("2. Нет!");

        switch (choice){
            case 1 -> {
                Post post = addPost(writer);
                postController.create(manager.mapPostToDto(post));
            }
            case 2 -> {
                System.out.println("Вы вернулись в главное меню");
                printMenu();
            }
            default -> System.out.println("Вы ввели неверный символ!");
        }


    }
}
