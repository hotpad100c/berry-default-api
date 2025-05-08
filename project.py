from project_template import *

def test (projectjson, properties):
    if os.path.exists (".cache/extramods/test.jar"): os.remove (".cache/extramods/test.jar")
    f = open ('.cache/extramods/test.jar', 'wb')
    f.write (open ('output/_test.jar', 'rb') .read ())
    f.close ()
    os.rename ("output/_test.jar", "output/test.jar")
