package dev.annyni.view;

import dev.annyni.controller.LabelController;
import dev.annyni.controller.PostController;
import dev.annyni.controller.WriterController;
import dev.annyni.dto.LabelDto;
import dev.annyni.dto.PostDto;
import dev.annyni.dto.WriterDto;
import dev.annyni.mapper.LabelMapper;
import dev.annyni.mapper.MapperManager;
import dev.annyni.mapper.PostMapper;
import dev.annyni.mapper.WriterMapper;
import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Status;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * todo Document type PostView
 */
@RequiredArgsConstructor
public class PostView {

    private final WriterController writerController;
    private final PostController postController;
    private final LabelController labelController;
    private final MapperManager manager;
//    private final LabelMapper labelMapper;
//    private final WriterMapper writerMapper;
//    private final PostMapper postMapper;
    private final Scanner scanner = new Scanner(System.in);

    private boolean waiting = true;

    public void printMenu(){
        while (waiting){
            System.out.println("Что бы вы хотели? Введите число.");
            System.out.println("1. Создать Post");
            System.out.println("2. Изменить Post");
            System.out.println("3. Найти Post по id");
            System.out.println("4. Найти все Posts");
            System.out.println("5. Удалить Post");
            System.out.println("6. Добавить Label в Post");
            System.out.println("7. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> createPost();
                case 2 -> updatePost();
                case 3 -> getPost();
                case 4 -> getAllPosts();
                case 5 -> deletePost();
                case 6 -> {
                    System.out.println("Выход из главного меню");
                    waiting = false;
                }
                default -> System.out.println("Вы ввели неверный символ!");
            }
        }
    }

    private void deletePost() {
        System.out.println("Введите id Post который хотите удалить: ");
        long postId = scanner.nextLong();

        postController.delete(postId);
        System.out.println("Post удален!");

    }

    private void getAllPosts() {
        List<PostDto> posts = postController.getAll();

        for (PostDto post : posts) {
            System.out.println(post);
        }
    }

    private void getPost() {
        System.out.println("Есть: ");
        getAllPosts();
        System.out.println("Введите id Writer которого вы хотите вывести: ");

        long postId = scanner.nextLong();
        scanner.nextLine();

        Optional<PostDto> post = postController.getById(postId);

        System.out.println("Post успешно найден! " + post);
    }

    private void updatePost() {
        System.out.println("Есть: ");
        getAllPosts();
        System.out.println("Введите id Post которого вы хотите изменить: ");

        Long postId = scanner.nextLong();
        scanner.nextLine();

        Optional<PostDto> existingPost = postController.getById(postId);
        if (existingPost.isPresent()){
            PostDto postDto = existingPost.get();
            Post post = manager.mapPostFromDto(postDto);

            if (post.getStatus() != Status.DELETED) {
                System.out.println("Введите обновленное содержание поста: ");
                String content = scanner.nextLine();

                List<LabelDto> newLabels = getLabels(post);

                Map<Long, Label> existingLabelMap = post.getLabels().stream()
                    .collect(Collectors.toMap(Label::getId, label -> label));

                System.out.println("Существующие метки:");
                for (Label label : post.getLabels()) {
                    System.out.println(label.getId() + ". " + label.getName());
                }

                List<Label> labelsToRemove = getDeleteLabels(post.getLabels());

                for (Label label : labelsToRemove) {
                    labelController.delete(label.getId());
                }

                for (LabelDto label : newLabels) {
                    if (label.id() == null) {
                        labelController.create(manager.mapLabelToDto(manager.mapLabelFromDto(label)));
                    }
                }

                post.getLabels().removeIf(labelsToRemove::contains);

                post.getLabels().retainAll(existingLabelMap.values());

                List<Label> correctLabels = newLabels.stream()
                    .map(manager::mapLabelFromDto)
                    .toList();

                post.getLabels().addAll(correctLabels);

                post.setContent(content);
                post.setUpdated(new Date());
                post.setStatus(Status.ACTIVE);

                postController.update(manager.mapPostToDto(post));
                System.out.println("Пост обновлен.");
            }else {
                System.out.println("Невозможно обновить удаленного поста");
            }
        }


    }

    private void createPost() {
        WriterDto writer = getWriter();

        System.out.println("Введите content: ");
        String content = scanner.nextLine();
        Status status = Status.ACTIVE;

        Post post = Post.builder()
            .content(content)
            .created(new Date())
            .updated(new Date())
            .status(status)
            .writer(manager.mapWriterFromDto(writer))
            .build();

        List<LabelDto> labels = getLabels(post);

        for (LabelDto label : labels) {
            labelController.create(label);
        }

        List<Label> newLabels = labels.stream()
            .map(manager::mapLabelFromDto)
            .toList();

        post.addLabels(newLabels);

        postController.create(manager.mapPostToDto(post));
        System.out.println("Post успешно создан. " + post);
    }

    private WriterDto getWriter(){
        System.out.println("Выберите Writer по ID: ");
        Long writerId = scanner.nextLong();
        scanner.nextLine();

        Optional<WriterDto> writer = writerController.getById(writerId);
        WriterDto writerDto = null;

        if (writer.isPresent()){
            writerDto = writer.get();
            System.out.println("Выбранный Writer: " + writerDto.firstname() + " " + writerDto.lastname() + " - " + writerDto.status());
        }

        return writerDto;
    }

    private List<LabelDto> getLabels(Post post) {
        List<LabelDto> labels = new ArrayList<>();

        boolean flag = true;

        while (flag){
            System.out.println("Добавить Label?");
            System.out.println("1. Да");
            System.out.println("2. Нет");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> {
                    System.out.println("Введите название Label: ");
                    String name = scanner.nextLine();

                    Label label = Label.builder()
                        .name(name)
                        .status(Status.ACTIVE)
                        .build();

                    labels.add(manager.mapLabelToDto(label));
                }
                case 2 -> flag = false;
                default -> System.out.println("Вы ввели неверный символ!");
            }
        }

        return labels;
    }

    private List<Label> getDeleteLabels(List<Label> labels) {
        List<Label> deleteLabels = new ArrayList<>();

        boolean flag = true;

        while (flag){
            System.out.println("Хотите удалить Label?");
            System.out.println("1. Да");
            System.out.println("2. Нет");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> {
                    System.out.println("Введите id Label который хотите удалить:");
                    long labelId = scanner.nextLong();

                    Label label = labels.stream()
                        .filter(l -> l.getId().equals(labelId))
                        .findFirst()
                        .orElse(null);

                    if (label != null){
                        deleteLabels.add(label);
                    } else {
                        System.out.println("Label с id " + labelId + " не найден.");
                    }
                }
                case 2 -> flag = false;
                default -> System.out.println("Вы ввели неверный символ!");
            }
        }

        return deleteLabels;
    }

}
