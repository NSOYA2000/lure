<template>
  <div id="app">
    <!-- 导航栏 -->
    <Navbar :current-page="currentPage" @navigate="handleNavigation" />

    <!-- 主要内容 -->
    <main>
      <Home v-if="currentPage === 'home'" @navigate-to-admin="handleNavigation('admin')" />
      <Admin v-if="currentPage === 'admin'" />
    </main>

    <!-- 页脚 -->
    <Footer />

    <!-- 全局模态框容器（用于鱼种详情） -->
    <div id="modal-container"></div>
  </div>
</template>

<script>
import { ref } from 'vue'
import Navbar from './components/Navbar.vue'
import Home from './views/Home.vue'
import Admin from './views/Admin.vue'
import Footer from './components/Footer.vue'
import './assets/style.css'

export default {
  name: 'App',
  components: {
    Navbar,
    Home,
    Admin,
    Footer
  },
  setup() {
    const currentPage = ref('home')

    const handleNavigation = (page) => {
      currentPage.value = page
      // 更新浏览器历史记录
      window.history.pushState({ page }, '', page === 'home' ? '/' : `/${page}`)
    }

    // 处理浏览器前进/后退
    window.addEventListener('popstate', (event) => {
      if (event.state && event.state.page) {
        currentPage.value = event.state.page
      } else {
        currentPage.value = 'home'
      }
    })

    // 初始化页面状态
    const path = window.location.pathname
    if (path.includes('admin')) {
      currentPage.value = 'admin'
      window.history.replaceState({ page: 'admin' }, '', '/admin')
    } else {
      window.history.replaceState({ page: 'home' }, '', '/')
    }

    return {
      currentPage,
      handleNavigation
    }
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

main {
  flex: 1;
}
</style>