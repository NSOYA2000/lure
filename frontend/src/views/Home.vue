<template>
  <div>
    <!-- Hero Section -->
    <header class="hero-section py-5 bg-light">
      <div class="container text-center">
        <h1 class="display-4 fw-bold text-primary">路亚鱼种百科全书</h1>
        <p class="lead">探索各种路亚目标鱼的详细资料、生活习性及钓鱼技巧</p>
        <div class="mt-4">
          <a href="#fish-list" class="btn btn-primary btn-lg me-3">
            <i class="fas fa-list"></i> 查看鱼种列表
          </a>
          <button class="btn btn-outline-primary btn-lg" @click="$emit('navigate-to-admin')">
            <i class="fas fa-edit"></i> 管理鱼种数据
          </button>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="container my-5">
      <!-- Introduction -->
      <section class="row mb-5">
        <div class="col-lg-8 mx-auto text-center">
          <h2 class="mb-4">什么是路亚钓鱼？</h2>
          <p class="fs-5">
            路亚钓鱼是一种使用仿生饵（路亚）模拟小鱼、昆虫等生物来诱骗目标鱼攻击的钓鱼方式。
            这种钓鱼方法环保、有趣，且对钓手的技巧要求较高。本网站收集了常见路亚目标鱼的详细信息，
            帮助钓友更好地了解目标鱼种，提高钓鱼成功率。
          </p>
        </div>
      </section>

      <!-- Fish List -->
      <section id="fish-list" class="mb-5">
        <h2 class="text-center mb-4">路亚目标鱼种</h2>
        <p class="text-center mb-5">点击鱼种卡片查看详细资料和钓鱼技巧</p>

        <!-- Fish cards -->
        <div class="row g-4">
          <template v-if="loading">
            <div class="col-12 text-center">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">加载中...</span>
              </div>
              <p class="mt-2">正在加载鱼种数据...</p>
            </div>
          </template>
          <template v-else-if="fishList.length === 0">
            <div class="col-12 empty-state">
              <i class="fas fa-fish"></i>
              <h3>暂无鱼种数据</h3>
              <p>请前往管理后台添加鱼种信息</p>
              <button class="btn btn-primary mt-3" @click="$emit('navigate-to-admin')">
                前往管理后台
              </button>
            </div>
          </template>
          <template v-else>
            <div class="col-md-6 col-lg-4" v-for="fish in fishList" :key="fish.id">
              <FishCard :fish="fish" @view-details="showFishDetails" />
            </div>
          </template>
        </div>
      </section>

      <!-- Tips Section -->
      <section class="bg-light p-5 rounded-3 mb-5">
        <h2 class="text-center mb-4">路亚钓鱼小贴士</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body">
                <h4 class="card-title"><i class="fas fa-water text-primary"></i> 选择正确的水层</h4>
                <p class="card-text">
                  不同的鱼种生活在不同的水层。了解目标鱼的活动水层，选择合适的路亚类型（浮水、沉水、悬停等）至关重要。
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body">
                <h4 class="card-title"><i class="fas fa-clock text-primary"></i> 把握最佳时机</h4>
                <p class="card-text">
                  大多数路亚目标鱼在清晨、傍晚或阴天时更为活跃。了解鱼的觅食时间可以大幅提高中鱼率。
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- Fish Details Modal -->
    <FishModal v-if="selectedFish" :fish="selectedFish" @close="selectedFish = null" />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import FishCard from '../components/FishCard.vue'
import FishModal from '../components/FishModal.vue'
import { fetchAllFish } from '../api/fishApi'

export default {
  name: 'Home',
  components: {
    FishCard,
    FishModal
  },
  emits: ['navigate-to-admin'],
  setup(props, { emit }) {
    const fishList = ref([])
    const loading = ref(true)
    const selectedFish = ref(null)

    const loadFishData = async () => {
      try {
        loading.value = true
        const data = await fetchAllFish()
        fishList.value = data
      } catch (error) {
        console.error('加载鱼种数据失败:', error)
      } finally {
        loading.value = false
      }
    }

    const showFishDetails = (fish) => {
      selectedFish.value = fish
    }

    const navigateToAdmin = () => {
      emit('navigate-to-admin')
    }

    onMounted(() => {
      loadFishData()
    })

    return {
      fishList,
      loading,
      selectedFish,
      showFishDetails,
      navigateToAdmin
    }
  }
}
</script>