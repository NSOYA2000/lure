<template>
  <div class="card fish-card shadow-sm h-100">
    <!-- Card Image -->
    <div class="card-img-top position-relative" style="height: 200px;">
      <img v-if="fish.imageUrl" :src="fish.imageUrl" class="w-100 h-100" :alt="fish.name" style="object-fit: cover;" @error="showPlaceholder">
      <div v-else class="w-100 h-100 d-flex align-items-center justify-content-center bg-light">
        <i class="fas fa-fish fa-4x text-muted"></i>
      </div>
    </div>

    <!-- Card Body -->
    <div class="card-body d-flex flex-column">
      <div class="d-flex justify-content-between align-items-start mb-2">
        <h5 class="card-title mb-0">{{ fish.name }}</h5>
        <span class="badge" :class="difficultyClass">{{ fish.difficulty }}难度</span>
      </div>
      <p class="text-muted small mb-2"><i>{{ fish.scientificName }}</i></p>
      <p class="card-text flex-grow-1">{{ truncatedDescription }}</p>

      <div class="mt-3">
        <!-- Badges -->
        <div class="d-flex flex-wrap gap-2 mb-3">
          <span class="badge bg-water">
            <i class="fas fa-water"></i> {{ fish.waterLayer }}
          </span>
          <span class="badge bg-season">
            <i class="fas fa-calendar-alt"></i> {{ seasonText }}
          </span>
          <span class="badge bg-dark">
            <i class="fas fa-ruler"></i> {{ sizeText }}
          </span>
        </div>

        <!-- View Details Button -->
        <div class="mt-auto">
          <button class="btn btn-outline-primary w-100" @click="viewDetails">
            <i class="fas fa-info-circle"></i> 查看详情
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from 'vue'

export default {
  name: 'FishCard',
  props: {
    fish: {
      type: Object,
      required: true
    }
  },
  emits: ['view-details'],
  setup(props, { emit }) {
    // 难度颜色映射
    const difficultyClass = computed(() => {
      const map = {
        '低': 'bg-success',
        '中等': 'bg-warning',
        '高': 'bg-danger'
      }
      return map[props.fish.difficulty] || 'bg-secondary'
    })

    // 截断描述
    const truncatedDescription = computed(() => {
      const desc = props.fish.description || ''
      return desc.length > 100 ? desc.substring(0, 100) + '...' : desc
    })

    // 季节文本（取第一部分）
    const seasonText = computed(() => {
      return props.fish.season ? props.fish.season.split('，')[0] : ''
    })

    // 尺寸文本（取第一部分）
    const sizeText = computed(() => {
      return props.fish.sizeRange ? props.fish.sizeRange.split('，')[0] : ''
    })

    // 图片加载失败处理
    const showPlaceholder = (event) => {
      event.target.style.display = 'none'
      // 理论上这里应该显示占位符，但我们已经有了v-else来处理
    }

    // 查看详情
    const viewDetails = () => {
      emit('view-details', props.fish)
    }

    return {
      difficultyClass,
      truncatedDescription,
      seasonText,
      sizeText,
      showPlaceholder,
      viewDetails
    }
  }
}
</script>