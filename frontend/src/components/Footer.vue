<template>
  <footer class="bg-dark text-white py-4 mt-auto">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <h5>路亚鱼种百科</h5>
          <p>一个为路亚钓鱼爱好者提供鱼种资料和技巧的网站</p>
        </div>
        <div class="col-md-6 text-md-end">
          <p>© 2026 路亚鱼种百科. 所有内容仅供参考。</p>
          <p>数据最后更新: <span id="last-update">{{ lastUpdateDate }}</span></p>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  name: 'Footer',
  setup() {
    const lastUpdateDate = ref('')

    onMounted(() => {
      // 设置最后更新日期为当天
      const today = new Date()
      lastUpdateDate.value = today.toISOString().split('T')[0]
    })

    return {
      lastUpdateDate
    }
  }
}
</script>