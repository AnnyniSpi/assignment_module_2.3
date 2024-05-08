package dev.annyni;

import dev.annyni.controller.LabelController;
import dev.annyni.controller.PostController;
import dev.annyni.controller.WriterController;
import dev.annyni.repository.LabelRepository;
import dev.annyni.repository.PostRepository;
import dev.annyni.repository.WriterRepository;
import dev.annyni.repository.imp.LabelRepositoryImpl;
import dev.annyni.repository.imp.PostRepositoryImpl;
import dev.annyni.repository.imp.WriterRepositoryImpl;
import dev.annyni.service.LabelService;
import dev.annyni.service.PostService;
import dev.annyni.service.WriterService;
import dev.annyni.view.LabelView;
import dev.annyni.view.PostView;
import dev.annyni.view.WriterView;
import lombok.RequiredArgsConstructor;

/**
 * todo Document type ProjectFactory
 */
@RequiredArgsConstructor
public class ProjectFactory {

    private final WriterRepository writerRepository = new WriterRepositoryImpl();
    private final PostRepository postRepository = new PostRepositoryImpl();
    private final LabelRepository labelRepository = new LabelRepositoryImpl();

    private final WriterService writerService = new WriterService(writerRepository);
    private final PostService postService = new PostService(postRepository);
    private final LabelService labelService = new LabelService(labelRepository);

    private final WriterController writerController = new WriterController(writerService);
    private final PostController postController = new PostController(postService);
    private final LabelController labelController = new LabelController(labelService);

    private final WriterView writerView = new WriterView(writerController, postController);
    private final PostView postView = new PostView(writerController,postController, labelController);
    private final LabelView labelView = new LabelView(labelController);

    public void writerRun() {
        writerView.printMenu();
    }

    public void postRun() {
        postView.printMenu();
    }

    public void labelRun() {
        labelView.printMenu();
    }
}
