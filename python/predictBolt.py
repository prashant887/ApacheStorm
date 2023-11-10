import storm

from sklearn.externals import joblib

class predictBolt(storm.BasicBolt):

	def initialize(self, conf, context):
		self._conf = conf
		self._context = context
		self._model = joblib.load('/Users/swethakolalapudi/SAModel.pkl') 
		self._vectorizer = joblib.load('/Users/swethakolalapudi/Vectorizer.pkl')
		self._label_map = {1:"positive",0:"negative"}

	def process(self, tup):
		label = self._model.predict(self._vectorizer.transform([tup.values[0]]))[0]
		storm.emit([tup.values[0],self._label_map[label]])
        

predictBolt().run()