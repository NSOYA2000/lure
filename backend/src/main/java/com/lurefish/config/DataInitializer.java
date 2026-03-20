package com.lurefish.config;

import com.lurefish.model.Fish;
import com.lurefish.repository.FishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FishRepository fishRepository;

    @Autowired
    public DataInitializer(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 如果数据库中没有数据，则初始化一些示例数据
        if (fishRepository.count() == 0) {
            List<Fish> initialFish = createInitialFishData();
            fishRepository.saveAll(initialFish);
            System.out.println("初始化了 " + initialFish.size() + " 条鱼种数据");
        }
    }

    private List<Fish> createInitialFishData() {
        Fish bass = new Fish();
        bass.setName("鲈鱼");
        bass.setScientificName("Lateolabrax japonicus");
        bass.setDescription("鲈鱼是常见的路亚目标鱼种，性情凶猛，捕食积极。广泛分布于中国沿海及淡水水域，是路亚钓手最喜爱的目标鱼之一。");
        bass.setHabitat("沿海河口、淡水湖泊、水库、河流中下游");
        bass.setSeason("全年可钓，春秋两季最佳");
        bass.setWaterLayer("中上层");
        bass.setDifficulty("中等");
        bass.setTackle("M或ML调路亚竿，2000-3000型纺车轮，PE线0.8-1.2号");
        bass.setLures(Arrays.asList("米诺", "铅笔", "波爬", "VIB", "软虫"));
        bass.setTechniques(Arrays.asList(
            "清晨和傍晚使用水面系饵（波爬、铅笔）效果最佳",
            "白天可使用潜深较大的米诺或VIB搜索中下层",
            "在障碍区边缘使用软虫跳底可吸引藏匿的鲈鱼",
            "收线速度不宜过快，保持饵的自然泳姿"
        ));
        bass.setTips(Arrays.asList(
            "鲈鱼对温度敏感，适宜水温为15-25℃",
            "阴天或小雨天气鲈鱼活性更高",
            "寻找水下结构如石头、沉木、水草边缘",
            "潮汐变化时鲈鱼捕食欲望强烈"
        ));
        bass.setSizeRange("常见30-60cm，最大可达1m以上");
        bass.setWeightRange("常见1-5kg");
        bass.setImageUrl("");

        Fish mandarinFish = new Fish();
        mandarinFish.setName("鳜鱼");
        mandarinFish.setScientificName("Siniperca chuatsi");
        mandarinFish.setDescription("鳜鱼又名桂花鱼，是中国本土的名贵淡水鱼种。性情凶猛，伏击型捕食者，对路亚饵反应强烈，是极具挑战性的路亚目标鱼。");
        mandarinFish.setHabitat("河流、湖泊、水库的岩石区、水草区、障碍物附近");
        mandarinFish.setSeason("春季到秋季，夏季活性最高");
        mandarinFish.setWaterLayer("底层");
        mandarinFish.setDifficulty("高");
        mandarinFish.setTackle("ML或M调路亚竿，2000型纺车轮，PE线0.6-1.0号");
        mandarinFish.setLures(Arrays.asList("铅头钩+软虫", "德州钓组", "倒吊钓组", "小克重VIB"));
        mandarinFish.setTechniques(Arrays.asList(
            "使用软虫跳底是钓获鳜鱼最有效的方法",
            "重点搜索岩石缝隙、水底结构边缘",
            "收线速度要慢，保持饵贴近水底",
            "感觉到障碍物时稍作停顿，鳜鱼常在此时发动攻击"
        ));
        mandarinFish.setTips(Arrays.asList(
            "鳜鱼喜欢清澈的水质，水质好的水域更容易钓获",
            "清晨和黄昏是钓鳜鱼的黄金时间",
            "使用自然色系的软虫效果更好",
            "鳜鱼攻击后不会立刻游走，中鱼后要迅速扬竿"
        ));
        mandarinFish.setSizeRange("常见25-40cm，最大可达70cm");
        mandarinFish.setWeightRange("常见0.5-2kg");
        mandarinFish.setImageUrl("");

        Fish blackFish = new Fish();
        blackFish.setName("黑鱼");
        blackFish.setScientificName("Channa argus");
        blackFish.setDescription("黑鱼又称乌鳢，是凶猛的掠食性鱼类，攻击力强，是路亚钓法中的热门目标鱼。喜欢潜伏在水草、障碍物附近发动突然袭击。");
        blackFish.setHabitat("湖泊、池塘、河流、沼泽等静水或缓流水域，尤其喜欢水草区");
        blackFish.setSeason("春末到秋季，夏季最为活跃");
        blackFish.setWaterLayer("中下层");
        blackFish.setDifficulty("高");
        blackFish.setTackle("H或MH调雷强竿，3000-4000型纺车轮，PE线2-3号");
        blackFish.setLures(Arrays.asList("雷蛙", "软虫", "米诺", "VIB", "铅笔"));
        blackFish.setTechniques(Arrays.asList(
            "雷蛙是钓黑鱼最经典的饵，在水草丛中缓慢拖行",
            "清晨和傍晚黑鱼活性最高，是作钓的最佳时机",
            "发现黑鱼炸水后要立即收紧余线，稍作停顿再扬竿",
            "障碍区边缘是黑鱼的伏击点，重点搜索这些区域"
        ));
        blackFish.setTips(Arrays.asList(
            "黑鱼对水温适应性强，但在25-30℃时活性最高",
            "阴天或小雨天气黑鱼更愿意到水面活动",
            "作钓时要保持安静，黑鱼对声音非常敏感",
            "中鱼后要迅速将鱼拉离障碍区，防止钻草"
        ));
        blackFish.setSizeRange("常见40-70cm，最大可达1m以上");
        blackFish.setWeightRange("常见2-5kg，最大可达10kg");
        blackFish.setImageUrl("");

        return Arrays.asList(bass, mandarinFish, blackFish);
    }
}