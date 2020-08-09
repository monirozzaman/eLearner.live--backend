from django.shortcuts import render
from rest_framework import viewsets
from .serializers import CourseSerializer
from .models import Courses
# Create your views here.

class CourseViewSet(viewsets.ModelViewSet):
    queryset = Courses.objects.all()
    serializer_class = CourseSerializer