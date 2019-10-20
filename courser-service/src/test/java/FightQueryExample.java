import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :haohaounique
 * @version :1.0.0
 * @Detaildescription :
 * @date : 2019/10/20
 */
public class FightQueryExample {
    private static List<String> fightCompany = Arrays.asList("CSA", "CEA", "BJ");

    public static void main(String[] args) {

        List<String> searchResults = search("SH", "BJ");
        System.out.printf("-----------------------result---------------------------------");
        System.out.println("");
        searchResults.forEach(System.out::println);
    }

    private static List<String> search(String original, String dest) {
        final List<String> result = new ArrayList<>();
        /**
         * stream() − 为集合创建串行流  parallelStream() − 为集合创建并行流
         *
         * 什么是 Stream？
         * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象
         * Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码
         * Stream（流）是一个来自数据源的元素队列并支持聚合操作
         *这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合
         *
         * 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
         * 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
         * 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
         * 和以前的Collection操作不同， Stream操作还有两个基础的特征：
         *
         * Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
         * 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现
         *
         */
        List<FightQueryTask> tasks = fightCompany.stream().map(f -> createSearchTask(f, original, dest)).collect(Collectors.toList());
        tasks.forEach(Thread::start);
        tasks.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tasks.stream().map(FightQueryTask::get).forEach(result::addAll);
        return result;
    }

    private static FightQueryTask createSearchTask(String f, String original, String dest) {
        return new FightQueryTask(f, original, dest);
    }
}
