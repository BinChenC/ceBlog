<template>
    <div class="m-container-small m-padded-tb-big">
        <div class="ui container">
            <div class="ui top attached segment teal">
                <div class="m-container m-padded-tb-big segment">
                    <div class="ui container">

                        <div style="padding: 0% 10% 0% 10%">
                            <el-table
                                    :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                                    style="width: 100%" lazy="lazy" :stripe="stripe" border>
                                <el-table-column
                                        label="Id"
                                        prop="id"
                                        align="center">
                                </el-table-column>

                                <el-table-column
                                        label="类别名称"
                                        prop="name"
                                        align="center">
                                </el-table-column>
                                <el-table-column
                                        label="操作"
                                        align="center">
                                    <template slot-scope="scope">
                                        <el-button
                                                size="mini"
                                                @click="handleEdit(scope.$index, scope.row)">修改
                                        </el-button>
                                        <el-button
                                                size="mini"
                                                type="danger"
                                                @click="handleDelete(scope.$index, scope.row)">删除
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>

                            <!--分页-->
                            <el-pagination
                                    background
                                    layout="prev, pager, next"
                                    :page-size="pageSize"
                                    :total="total"
                                    @current-change="page"
                                    style="margin-top: 2em;background-color: rgba(145,213,25,0.2)">
                            </el-pagination>
                            <div class="field m-mobile-wide m-margin-top">
                                <button @click="handleAdd" class="ui teal button m-mobile-wide"><i
                                        class="edit icon"></i>新增
                                </button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>



</template>

<script>
    export default {
        name: 'ATypes',
        inject: ["reload"],

        methods: {
            //新增
            handleAdd() {
                this.$router.push({
                    path: '/a_type_input',
                    name: 'typeInput',
                    params: {
                        key: 'add',
                    },
                });
            },
            // 修改
            handleEdit(index, row) {
                this.$router.push({
                    path: '/a_type_input',
                    name: 'typeInput',
                    params: {
                        id: row.id,
                        name: row.name,
                        key: 'edit',
                    },
                });
            },
            // 删除
            handleDelete(index, row) {
                this.$confirm('是否删除当前分类呢？', '提示', {
                    confirmButtonText: '删除',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete('/blog/admin/types/' + row.id).then(resp => {
                        if (resp.data == 'success') {
                            this.$message({
                                offset: 140,
                                message: '删除成功~',
                                type: 'success',
                                duration: 1500
                            });
                            this.reload();

                        } else if (resp.data == 'notExists') {
                            this.$message({
                                offset: 140,
                                message: '当前类别不存在，换一个试试吧~',
                                type: 'warning',
                                duration: 1500
                            });
                        } else {
                            this.$message({
                                offset: 140,
                                message: '服务器可能出现了点异常QAQ',
                                type: 'error',
                                duration: 1500
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        offset: 140,
                        message: '取消删除',
                        type: 'info',
                        duration: 1500
                    });
                });

            },
            // 分页
            page(currentPage) {
                axios.get('/blog/admin/types/' + (currentPage - 1) + '/5').then(resp => {
                    if (resp.data != null) {
                        this.tableData = resp.data.content;
                        this.pageSize = resp.data.size;
                        this.total = resp.data.totalElements;
                    } else {
                        this.$message({
                            offset: 140,
                            message: '服务器可能出现了点异常QAQ',
                            type: 'error',
                            duration: 1500
                        });
                    }
                })
            }
        },
        data() {
            return {
                region: '',
                search: '',
                stripe: true,
                isA: true,
                pageSize: 1,
                total: 1,
                tableData: [],
                lazy: true
            }
        },
        created() {
            axios.get('/blog/admin/types/0/5').then(resp => {
                this.tableData = resp.data.content
                this.pageSize = resp.data.size
                this.total = resp.data.totalElements
            })
        }
    }
</script>

<style scoped>
    .el-select .el-input__inner {

        width: 20px;

    }
</style>