package dev.annyni;

import dev.annyni.controller.LabelController;
import dev.annyni.controller.PostController;
import dev.annyni.controller.WriterController;
import dev.annyni.mapper.LabelMapper;
import dev.annyni.mapper.MapperManager;
import dev.annyni.mapper.PostMapper;
import dev.annyni.mapper.WriterMapper;
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

    private final LabelMapper labelMapper;
    private final WriterMapper writerMapper;
    private final PostMapper postMapper;
    private final MapperManager mapperManager = new MapperManager(labelMapper, postMapper, writerMapper);

    private final WriterRepository writerRepository = new WriterRepositoryImpl();
    private final PostRepository postRepository = new PostRepositoryImpl();
    private final LabelRepository labelRepository = new LabelRepositoryImpl();

    private final WriterService writerService = new WriterService(writerRepository, mapperManager);
    private final PostService postService = new PostService(postRepository, mapperManager);
    private final LabelService labelService = new LabelService(labelRepository, mapperManager);

    private final WriterController writerController = new WriterController(writerService);
    private final PostController postController = new PostController(postService);
    private final LabelController labelController = new LabelController(labelService);

    private final WriterView writerView = new WriterView(writerController, postController, mapperManager);
    private final PostView postView = new PostView(writerController,postController, labelController, mapperManager);
    private final LabelView labelView = new LabelView(labelController, mapperManager);

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
