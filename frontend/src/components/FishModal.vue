<template>
  <div class="modal fade show d-block" tabindex="-1" aria-hidden="false" style="background-color: rgba(0, 0, 0, 0.5);">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ fish.name }} - 详细资料</h5>
          <button type="button" class="btn-close" @click="close" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="row">
            <!-- Fish Image -->
            <div class="col-md-4 text-center mb-4 mb-md-0">
              <div class="bg-light p-4 rounded">
                <div class="position-relative" style="height: 200px;">
                  <img v-if="fish.imageUrl" :src="fish.imageUrl" class="img-fluid rounded w-100 h-100" :alt="fish.name" style="object-fit: cover;" @error="showPlaceholder">
                  <div v-else class="w-100 h-100 d-flex align-items-center justify-content-center">
                    <i class="fas fa-fish fa-6x text-primary"></i>
                  </div>
                </div>
                <h5 class="mt-3">{{ fish.name }}</h5>
                <p class="text-muted"><i>{{ fish.scientificName }}</i></p>
              </div>
            </div>

            <!-- Fish Details -->
            <div class="col-md-8">
              <!-- Basic Information -->
              <h6>基本资料</h6>
              <table class="table table-sm">
                <tr>
                  <th width="30%">栖息环境:</th>
                  <td>{{ fish.habitat }}</td>
                </tr>
                <tr>
                  <th>适宜季节:</th>
                  <td>{{ fish.season }}</td>
                </tr>
                <tr>
                  <th>活动水层:</th>
                  <td>{{ fish.waterLayer }}</td>
                </tr>
                <tr>
                  <th>尺寸范围:</th>
                  <td>{{ fish.sizeRange }}</td>
                </tr>
                <tr>
                  <th>体重范围:</th>
                  <td>{{ fish.weightRange }}</td>
                </tr>
                <tr>
                  <th>作钓难度:</th>
                  <td><span class="badge" :class="difficultyBadgeClass">{{ fish.difficulty }}</span></td>
                </tr>
              </table>

              <!-- Recommended Tackle -->
              <h6 class="mt-4">推荐装备</h6>
              <p>{{ fish.tackle }}</p>

              <!-- Lures -->
              <h6 class="mt-4">适用路亚饵</h6>
              <div class="d-flex flex-wrap gap-2 mb-4">
                <span v-for="(lure, index) in fish.lures" :key="index" class="badge bg-info">
                  {{ lure }}
                </span>
              </div>

              <!-- Techniques -->
              <h6 class="mt-4">作钓技巧</h6>
              <ul class="tips-list">
                <li v-for="(tech, index) in fish.techniques" :key="index">
                  <i class="fas fa-check-circle"></i> {{ tech }}
                </li>
              </ul>

              <!-- Tips -->
              <h6 class="mt-4">小贴士</h6>
              <ul class="tips-list">
                <li v-for="(tip, index) in fish.tips" :key="index">
                  <i class="fas fa-lightbulb"></i> {{ tip }}
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="close">关闭</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from 'vue'

export default {
  name: 'FishModal',
  props: {
    fish: {
      type: Object,
      required: true
    }
  },
  emits: ['close'],
  setup(props, { emit }) {
    // 难度徽章类名
    const difficultyBadgeClass = computed(() => {
      switch (props.fish.difficulty) {
        case '低': return 'bg-success'
        case '中等': return 'bg-warning'
        case '高': return 'bg-danger'
        default: return 'bg-secondary'
      }
    })

    // 关闭模态框
    const close = () => {
      emit('close')
    }

    // 图片加载失败处理
    const showPlaceholder = (event) => {
      event.target.style.display = 'none'
      // 占位符已经在v-else中显示
    }

    // 点击背景关闭
    const handleBackdropClick = (event) => {
      if (event.target.classList.contains('modal')) {
        close()
      }
    }

    // 按ESC键关闭
    const handleKeydown = (event) => {
      if (event.key === 'Escape') {
        close()
      }
    }

    return {
      difficultyBadgeClass,
      close,
      showPlaceholder,
      handleBackdropClick,
      handleKeydown
    }
  },
  mounted() {
    // 添加事件监听器
    document.addEventListener('keydown', this.handleKeydown)
    document.addEventListener('click', this.handleBackdropClick)

    // 防止背景滚动
    document.body.style.overflow = 'hidden'
  },
  beforeUnmount() {
    // 移除事件监听器
    document.removeEventListener('keydown', this.handleKeydown)
    document.removeEventListener('click', this.handleBackdropClick)

    // 恢复背景滚动
    document.body.style.overflow = ''
  }
}
</script>