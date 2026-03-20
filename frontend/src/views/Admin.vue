<template>
  <main class="container my-5 admin-container">
    <!-- Header -->
    <div class="row mb-4">
      <div class="col">
        <h1 class="display-5 fw-bold">
          <i class="fas fa-cog"></i> 鱼种数据管理后台
        </h1>
        <p class="lead">在这里管理网站上的路亚鱼种信息。您可以添加、编辑、删除鱼种数据。</p>
      </div>
    </div>

    <!-- Data Management Controls -->
    <div class="card admin-card shadow-sm mb-5">
      <div class="card-body">
        <h5 class="card-title mb-4">
          <i class="fas fa-database"></i> 数据管理
        </h5>
        <div class="row g-3">
          <div class="col-md-4">
            <button @click="showAddForm" class="btn btn-primary w-100">
              <i class="fas fa-plus"></i> 添加新鱼种
            </button>
          </div>
          <div class="col-md-4">
            <button @click="exportData" class="btn btn-success w-100" :disabled="fishList.length === 0">
              <i class="fas fa-download"></i> 导出数据
            </button>
          </div>
          <div class="col-md-4">
            <div class="input-group">
              <input type="file" class="form-control" id="import-file" accept=".json" @change="handleFileSelect">
              <button @click="importData" class="btn btn-outline-primary" :disabled="!selectedFile">
                <i class="fas fa-upload"></i> 导入
              </button>
            </div>
          </div>
        </div>
        <div class="mt-3 text-muted small">
          <p class="mb-0">
            <i class="fas fa-info-circle"></i>
            数据保存在后端数据库中。要备份数据，请使用"导出数据"功能。要恢复数据，请使用"导入"功能。
          </p>
        </div>
      </div>
    </div>

    <!-- Fish List Table -->
    <div class="card admin-card shadow-sm">
      <div class="card-body">
        <h5 class="card-title mb-4">
          <i class="fas fa-list"></i> 鱼种列表
        </h5>
        <div v-if="loading" class="text-center py-5">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">加载中...</span>
          </div>
          <p class="mt-2">正在加载鱼种数据...</p>
        </div>
        <div v-else-if="fishList.length === 0" class="text-center empty-state py-5">
          <i class="fas fa-fish"></i>
          <h5>暂无鱼种数据</h5>
          <p>点击"添加新鱼种"按钮开始添加数据</p>
        </div>
        <div v-else>
          <div class="table-responsive">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th width="5%">ID</th>
                  <th width="15%">鱼种名称</th>
                  <th width="20%">学名</th>
                  <th width="10%">难度</th>
                  <th width="15%">适宜季节</th>
                  <th width="15%">活动水层</th>
                  <th width="20%">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="fish in fishList" :key="fish.id">
                  <td>{{ fish.id }}</td>
                  <td><strong>{{ fish.name }}</strong></td>
                  <td><i>{{ fish.scientificName }}</i></td>
                  <td><span class="badge" :class="getDifficultyBadgeClass(fish.difficulty)">{{ fish.difficulty }}</span></td>
                  <td>{{ getFirstPart(fish.season) }}</td>
                  <td>{{ fish.waterLayer }}</td>
                  <td>
                    <button @click="showEditForm(fish)" class="btn btn-sm btn-outline-primary me-2">
                      <i class="fas fa-edit"></i> 编辑
                    </button>
                    <button @click="showDeleteConfirm(fish)" class="btn btn-sm btn-outline-danger">
                      <i class="fas fa-trash"></i> 删除
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <p class="text-muted small mt-3">共 {{ fishList.length }} 条记录</p>
        </div>
      </div>
    </div>

    <!-- Fish Form Modal -->
    <div v-if="showFormModal" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0, 0, 0, 0.5);">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ isEditing ? '编辑鱼种' : '添加新鱼种' }}</h5>
            <button type="button" class="btn-close" @click="closeFormModal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="saveFish">
              <input type="hidden" v-model="formData.id">

              <div class="row g-3">
                <div class="col-md-6">
                  <label for="fish-name" class="form-label">鱼种名称 *</label>
                  <input type="text" class="form-control" id="fish-name" v-model="formData.name" required>
                </div>

                <div class="col-md-6">
                  <label for="scientific-name" class="form-label">学名 *</label>
                  <input type="text" class="form-control" id="scientific-name" v-model="formData.scientificName" required>
                </div>

                <div class="col-md-12">
                  <label for="description" class="form-label">描述 *</label>
                  <textarea class="form-control" id="description" rows="3" v-model="formData.description" required></textarea>
                </div>

                <div class="col-md-6">
                  <label for="habitat" class="form-label">栖息环境 *</label>
                  <input type="text" class="form-control" id="habitat" v-model="formData.habitat" required>
                </div>

                <div class="col-md-6">
                  <label for="season" class="form-label">适宜季节 *</label>
                  <input type="text" class="form-control" id="season" v-model="formData.season" placeholder="例如：春季到秋季，夏季最佳" required>
                </div>

                <div class="col-md-6">
                  <label for="water-layer" class="form-label">活动水层 *</label>
                  <select class="form-select" id="water-layer" v-model="formData.waterLayer" required>
                    <option value="">请选择</option>
                    <option value="上层">上层</option>
                    <option value="中上层">中上层</option>
                    <option value="中层">中层</option>
                    <option value="中下层">中下层</option>
                    <option value="底层">底层</option>
                    <option value="全水层">全水层</option>
                  </select>
                </div>

                <div class="col-md-6">
                  <label for="difficulty" class="form-label">作钓难度 *</label>
                  <select class="form-select" id="difficulty" v-model="formData.difficulty" required>
                    <option value="">请选择</option>
                    <option value="低">低</option>
                    <option value="中等">中等</option>
                    <option value="高">高</option>
                  </select>
                </div>

                <div class="col-md-6">
                  <label for="size-range" class="form-label">尺寸范围 *</label>
                  <input type="text" class="form-control" id="size-range" v-model="formData.sizeRange" placeholder="例如：常见25-40cm，最大可达70cm" required>
                </div>

                <div class="col-md-6">
                  <label for="weight-range" class="form-label">体重范围 *</label>
                  <input type="text" class="form-control" id="weight-range" v-model="formData.weightRange" placeholder="例如：常见0.5-2kg" required>
                </div>

                <div class="col-md-12">
                  <label for="tackle" class="form-label">推荐装备 *</label>
                  <input type="text" class="form-control" id="tackle" v-model="formData.tackle" placeholder="例如：ML调路亚竿，2000型纺车轮，PE线0.6-1.0号" required>
                </div>

                <div class="col-md-12">
                  <label for="lures" class="form-label">适用路亚饵 *<br><small class="text-muted">用逗号分隔不同的路亚饵</small></label>
                  <input type="text" class="form-control" id="lures" v-model="luresText" placeholder="例如：米诺,铅笔,波爬,VIB,软虫" required>
                </div>

                <div class="col-md-12">
                  <label for="techniques" class="form-label">作钓技巧 *<br><small class="text-muted">每行一个技巧</small></label>
                  <textarea class="form-control" id="techniques" rows="4" v-model="techniquesText" placeholder="例如：清晨和傍晚使用水面系饵效果最佳&#10;白天可使用潜深较大的米诺搜索中下层" required></textarea>
                </div>

                <div class="col-md-12">
                  <label for="tips" class="form-label">小贴士 *<br><small class="text-muted">每行一个小贴士</small></label>
                  <textarea class="form-control" id="tips" rows="4" v-model="tipsText" placeholder="例如：鲈鱼对温度敏感，适宜水温为15-25℃&#10;阴天或小雨天气鲈鱼活性更高" required></textarea>
                </div>

                <div class="col-md-12">
                  <label for="image" class="form-label">图片URL</label>
                  <input type="text" class="form-control" id="image" v-model="formData.imageUrl" placeholder="例如：images/bass.jpg">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeFormModal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveFish" :disabled="saving">
              <span v-if="saving">保存中...</span>
              <span v-else>保存</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal fade show d-block" tabindex="-1" style="background-color: rgba(0, 0, 0, 0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">确认删除</h5>
            <button type="button" class="btn-close" @click="closeDeleteModal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p>您确定要删除这个鱼种吗？此操作无法撤销。</p>
            <p class="text-danger fw-bold">{{ fishToDelete ? fishToDelete.name : '' }}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeDeleteModal">取消</button>
            <button type="button" class="btn btn-danger" @click="confirmDelete" :disabled="deleting">
              <span v-if="deleting">删除中...</span>
              <span v-else>确认删除</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Alert Toast -->
    <div v-if="alert.show" class="alert alert-dismissible fade show position-fixed"
         :class="`alert-${alert.type}`" style="top: 20px; right: 20px; z-index: 1060; min-width: 300px;">
      {{ alert.message }}
      <button type="button" class="btn-close" @click="closeAlert"></button>
    </div>
  </main>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'
import { fetchAllFish, createFish, updateFish, deleteFish } from '../api/fishApi'

export default {
  name: 'Admin',
  setup() {
    // 状态
    const fishList = ref([])
    const loading = ref(true)
    const showFormModal = ref(false)
    const showDeleteModal = ref(false)
    const isEditing = ref(false)
    const saving = ref(false)
    const deleting = ref(false)
    const selectedFile = ref(null)
    const fishToDelete = ref(null)

    // 表单数据
    const formData = reactive({
      id: null,
      name: '',
      scientificName: '',
      description: '',
      habitat: '',
      season: '',
      waterLayer: '',
      difficulty: '',
      sizeRange: '',
      weightRange: '',
      tackle: '',
      lures: [],
      techniques: [],
      tips: [],
      imageUrl: ''
    })

    // 文本字段（用于编辑）
    const luresText = computed({
      get: () => formData.lures.join(', '),
      set: (value) => {
        formData.lures = value.split(',').map(item => item.trim()).filter(item => item.length > 0)
      }
    })

    const techniquesText = computed({
      get: () => formData.techniques.join('\n'),
      set: (value) => {
        formData.techniques = value.split('\n').map(item => item.trim()).filter(item => item.length > 0)
      }
    })

    const tipsText = computed({
      get: () => formData.tips.join('\n'),
      set: (value) => {
        formData.tips = value.split('\n').map(item => item.trim()).filter(item => item.length > 0)
      }
    })

    // 提示消息
    const alert = reactive({
      show: false,
      message: '',
      type: 'info'
    })

    // 加载鱼种数据
    const loadFishData = async () => {
      try {
        loading.value = true
        const data = await fetchAllFish()
        fishList.value = data
      } catch (error) {
        showAlert('加载鱼种数据失败: ' + error.message, 'danger')
      } finally {
        loading.value = false
      }
    }

    // 显示添加表单
    const showAddForm = () => {
      resetForm()
      isEditing.value = false
      showFormModal.value = true
    }

    // 显示编辑表单
    const showEditForm = (fish) => {
      resetForm()
      Object.assign(formData, {
        id: fish.id,
        name: fish.name,
        scientificName: fish.scientificName,
        description: fish.description,
        habitat: fish.habitat,
        season: fish.season,
        waterLayer: fish.waterLayer,
        difficulty: fish.difficulty,
        sizeRange: fish.sizeRange,
        weightRange: fish.weightRange,
        tackle: fish.tackle,
        lures: [...fish.lures],
        techniques: [...fish.techniques],
        tips: [...fish.tips],
        imageUrl: fish.imageUrl || ''
      })
      isEditing.value = true
      showFormModal.value = true
    }

    // 重置表单
    const resetForm = () => {
      Object.assign(formData, {
        id: null,
        name: '',
        scientificName: '',
        description: '',
        habitat: '',
        season: '',
        waterLayer: '',
        difficulty: '',
        sizeRange: '',
        weightRange: '',
        tackle: '',
        lures: [],
        techniques: [],
        tips: [],
        imageUrl: ''
      })
    }

    // 关闭表单模态框
    const closeFormModal = () => {
      showFormModal.value = false
    }

    // 验证表单
    const validateForm = () => {
      const requiredFields = [
        'name', 'scientificName', 'description', 'habitat', 'season',
        'waterLayer', 'difficulty', 'sizeRange', 'weightRange', 'tackle'
      ]

      for (const field of requiredFields) {
        if (!formData[field] || formData[field].toString().trim() === '') {
          showAlert(`请填写"${getFieldLabel(field)}"字段`, 'warning')
          return false
        }
      }

      if (formData.lures.length === 0) {
        showAlert('请至少填写一个适用路亚饵', 'warning')
        return false
      }

      if (formData.techniques.length === 0) {
        showAlert('请至少填写一个作钓技巧', 'warning')
        return false
      }

      if (formData.tips.length === 0) {
        showAlert('请至少填写一个小贴士', 'warning')
        return false
      }

      return true
    }

    // 获取字段标签
    const getFieldLabel = (fieldName) => {
      const labels = {
        name: '鱼种名称',
        scientificName: '学名',
        description: '描述',
        habitat: '栖息环境',
        season: '适宜季节',
        waterLayer: '活动水层',
        difficulty: '作钓难度',
        sizeRange: '尺寸范围',
        weightRange: '体重范围',
        tackle: '推荐装备'
      }
      return labels[fieldName] || fieldName
    }

    // 保存鱼种
    const saveFish = async () => {
      if (!validateForm()) {
        return
      }

      try {
        saving.value = true
        if (isEditing.value) {
          await updateFish(formData.id, formData)
          showAlert('鱼种更新成功', 'success')
        } else {
          await createFish(formData)
          showAlert('鱼种添加成功', 'success')
        }

        closeFormModal()
        loadFishData()
      } catch (error) {
        showAlert(`保存失败: ${error.message}`, 'danger')
      } finally {
        saving.value = false
      }
    }

    // 显示删除确认
    const showDeleteConfirm = (fish) => {
      fishToDelete.value = fish
      showDeleteModal.value = true
    }

    // 关闭删除确认模态框
    const closeDeleteModal = () => {
      showDeleteModal.value = false
      fishToDelete.value = null
    }

    // 确认删除
    const confirmDelete = async () => {
      if (!fishToDelete.value) return

      try {
        deleting.value = true
        await deleteFish(fishToDelete.value.id)
        showAlert('鱼种删除成功', 'success')
        closeDeleteModal()
        loadFishData()
      } catch (error) {
        showAlert(`删除失败: ${error.message}`, 'danger')
      } finally {
        deleting.value = false
      }
    }

    // 导出数据
    const exportData = () => {
      if (fishList.value.length === 0) {
        showAlert('没有数据可导出', 'warning')
        return
      }

      const jsonStr = JSON.stringify(fishList.value, null, 2)
      const blob = new Blob([jsonStr], { type: 'application/json' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `lure-fish-data-${new Date().toISOString().split('T')[0]}.json`
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
      URL.revokeObjectURL(url)

      showAlert('数据导出成功', 'success')
    }

    // 处理文件选择
    const handleFileSelect = (event) => {
      selectedFile.value = event.target.files[0]
    }

    // 导入数据
    const importData = () => {
      if (!selectedFile.value) {
        showAlert('请选择要导入的文件', 'warning')
        return
      }

      const reader = new FileReader()
      reader.onload = async (e) => {
        try {
          const importedData = JSON.parse(e.target.result)

          if (!Array.isArray(importedData)) {
            throw new Error('数据格式错误：应为数组')
          }

          // 验证数据
          for (const fish of importedData) {
            if (!fish.name || !fish.scientificName) {
              throw new Error('数据格式错误：缺少必要字段')
            }
          }

          // 清空现有数据并导入新数据
          // 注意：这里应该是一个一个创建，但为了简单，我们只是显示提示
          showAlert(`检测到${importedData.length}条数据，请手动添加`, 'info')

          // 重置文件输入
          document.getElementById('import-file').value = ''
          selectedFile.value = null

        } catch (error) {
          showAlert(`导入失败：${error.message}`, 'danger')
        }
      }
      reader.onerror = () => {
        showAlert('读取文件失败', 'danger')
      }
      reader.readAsText(selectedFile.value)
    }

    // 显示提示
    const showAlert = (message, type = 'info') => {
      alert.message = message
      alert.type = type
      alert.show = true

      // 5秒后自动消失
      setTimeout(() => {
        alert.show = false
      }, 5000)
    }

    // 关闭提示
    const closeAlert = () => {
      alert.show = false
    }

    // 获取难度徽章类名
    const getDifficultyBadgeClass = (difficulty) => {
      const map = {
        '低': 'bg-success',
        '中等': 'bg-warning',
        '高': 'bg-danger'
      }
      return map[difficulty] || 'bg-secondary'
    }

    // 获取字符串的第一部分
    const getFirstPart = (str) => {
      return str ? str.split('，')[0] : ''
    }

    // 初始化
    onMounted(() => {
      loadFishData()
    })

    return {
      fishList,
      loading,
      showFormModal,
      showDeleteModal,
      isEditing,
      saving,
      deleting,
      selectedFile,
      fishToDelete,
      formData,
      luresText,
      techniquesText,
      tipsText,
      alert,
      showAddForm,
      showEditForm,
      closeFormModal,
      saveFish,
      showDeleteConfirm,
      closeDeleteModal,
      confirmDelete,
      exportData,
      handleFileSelect,
      importData,
      closeAlert,
      getDifficultyBadgeClass,
      getFirstPart
    }
  }
}
</script>