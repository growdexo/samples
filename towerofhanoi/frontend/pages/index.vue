<template>
  <section class="section">
    <div class="columns is-mobile">
       <b-field label="Number of Disks">
            <b-input v-model="numDisks"></b-input>
            <p class="control">
                <b-button class="button is-primary"  @click="go">Go</b-button>
            </p>
        </b-field>
    </div>
    <div class="columns" v-if="states && states.length > 0">
      <b-button type="is-primary is-light" :disabled="this.statePointer == 0" @click="prevState">Prev</b-button>
      <b-button type="is-primary is-light" :disabled="this.statePointer == this.states.length - 1" @click="nextState">Next</b-button>
    </div>

    <div class="columns" v-if="states && states.length > 0">
      <div class="rod">
        <div v-for="disk in firstTower" :key="disk.diskId" :style="disk.style" class="disk">
            {{disk.diskId}}
        </div>
      </div>
      <div class="rod">
        <div v-for="disk in secondTower" :key="disk.diskId" :style="disk.style" class="disk">
            {{disk.diskId}}
        </div>
      </div>
      <div class="rod">
        <div v-for="disk in thirdTower" :key="disk.diskId" :style="disk.style" class="disk">
            {{disk.diskId}}
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import distinctColors from 'distinct-colors'

const DISK_MAX_WIDTH = 150;
const BOTTOM = 48;
const DISK_HEIGHT = 30;
const ROD_WIDTH = 30;
const COLORS = distinctColors({count: 10})

function buildTower(n, disks) {
  const diskMinWidth = DISK_MAX_WIDTH / 2;
  const diskWidthIncrements = diskMinWidth / n;
  const result = [];
  
  for (let i = 0; i < disks.length; i++) {
    const diskWidth = diskMinWidth + disks[i] * diskWidthIncrements
    const diskId = disks[i]

    result.push({
      diskId,
      style: {
        backgroundColor: COLORS[(diskId - 1)].hex(),
        bottom: BOTTOM + (DISK_HEIGHT * i) + 'px',
        width: diskWidth + 'px',
        marginLeft: '-' + (diskWidth - ROD_WIDTH)/2 +'px'
      }
    });
  }

  return result;
}

export default {
  name: 'HomePage',

  components: {

  },

  computed: {
    firstTower() {
      const state = this.states[this.statePointer].stacks[0]
      console.log(buildTower(this.n, state))
      
      return buildTower(this.n, state)
    },

    secondTower() {
      const state = this.states[this.statePointer].stacks[1]
      return buildTower(this.n, state)
    },

    thirdTower() {
      const state = this.states[this.statePointer].stacks[2]
      return buildTower(this.n, state)
    }
  },

  methods: {
    async go() {
      if (!this.numDisks) {
        return;
      }
      this.statePointer = 0;
      const getStatesResult = await this.$axios.$get('http://localhost:8080/towerofhanoi?n=' + this.numDisks)
      this.n = this.numDisks
      this.states = getStatesResult.data
    },

    nextState() {
      if (this.statePointer < this.states.length - 1) {
        this.statePointer ++
      }
    },

    prevState() {
      if (this.statePointer > 0) {
        this.statePointer --;
      }
    }
  },

  data() {
    return {
      n: 3,
      numDisks: 3,
      states: null,
      statePointer: 0,
    }
  }
}
</script>

<style scoped>
  .rod {
    width: 30px;
    height: 300px;
    background-color: brown;
    margin-top: 30px;
    margin-left: 250px;
  }

  .disk {
    background-color: black;
    height: 30px;
    position: absolute;
    bottom: 48px;
    text-align: center;
    color: white;
  }
</style>
