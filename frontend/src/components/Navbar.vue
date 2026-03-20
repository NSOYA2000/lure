<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
      <a class="navbar-brand" href="#" @click.prevent="navigate('home')">
        <i class="fas fa-fish"></i> 路亚鱼种百科
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <a class="nav-link" :class="{ active: currentPage === 'home' }" href="#" @click.prevent="navigate('home')">
              <i class="fas fa-home"></i> 首页
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" :class="{ active: currentPage === 'admin' }" href="#" @click.prevent="navigate('admin')">
              <i class="fas fa-cog"></i> 管理后台
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
  props: {
    currentPage: {
      type: String,
      default: 'home'
    }
  },
  emits: ['navigate'],
  methods: {
    navigate(page) {
      this.$emit('navigate', page)
    }
  }
}
</script>